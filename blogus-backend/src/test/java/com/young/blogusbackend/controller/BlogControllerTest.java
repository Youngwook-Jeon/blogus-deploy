package com.young.blogusbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.young.blogusbackend.dto.BlogRequest;
import com.young.blogusbackend.model.Blog;
import com.young.blogusbackend.model.Bloger;
import com.young.blogusbackend.model.Category;
import com.young.blogusbackend.repository.BlogRepository;
import com.young.blogusbackend.repository.BlogerRepository;
import com.young.blogusbackend.repository.CategoryRepository;
import com.young.blogusbackend.service.MailService;
import com.young.blogusbackend.util.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
@Transactional
public class BlogControllerTest extends AbstractContainerBaseTest {

    Category category;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogerRepository blogerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    MailService mailService;

    @BeforeEach
    void init() {
        category = categoryRepository.save(CategoryTestUtil.createValidCategory());
    }

    void initSearching() {
        Bloger bloger = blogerRepository.save(AuthTestUtil.createValidUser());

        Blog blog1 = BlogTestUtil.createValidBlog();
        blog1.setBloger(bloger);
        blog1.setCategory(category);
        blogRepository.save(blog1);

        Blog blog2 = BlogTestUtil.createValidBlog();
        blog2.setBloger(bloger);
        blog2.setCategory(category);
        blogRepository.save(blog2);

        Blog blog3 = BlogTestUtil.createValidBlog();
        blog3.setBloger(bloger);
        blog3.setCategory(category);
        blog3.setTitle("NO MATCHING TITLE!!!");
        blog3.setDescription("NO MATCHING DESCRIPTION!!!");
        blogRepository.save(blog3);
    }

    @DisplayName("test for searching with given keyword")
    @Test
    void testSearch() throws Exception {
        // Setup
        String keyword = "java";
        initSearching();

        // Run the test
        ResultActions resultActions = mockMvc.perform(get("/search/blogs")
                .param("keyword", keyword)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Verify the results
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].description", is(BlogTestUtil.DESCRIPTION)))
                .andExpect(jsonPath("$[1].description", is(BlogTestUtil.DESCRIPTION)));

    }

    @DisplayName("test for creating a valid blog having both a category and a user")
    @Test
    @WithMockCustomUser
    void testCreate_whenGivenRequestIsValid() throws Exception {
        // Setup
        String title = "spring boot";
        String content = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.";
        String description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        String thumbnail = "http://www.example.com/thumbnail";
        String categoryName = category.getName();
        BlogRequest blogRequest = new BlogRequest(title, content, description, thumbnail, categoryName);

        // Run the test
        ResultActions resultActions = mockMvc.perform(post("/blogs")
                .content(objectMapper.writeValueAsString(blogRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Verify the results
        Optional<Blog> blogOptional = blogRepository.findById(1L);
        assertThat(blogOptional.isPresent()).isTrue();
        assertThat(blogOptional.get().getTitle()).isEqualTo(title);

        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.category.name", is(categoryName)))
                .andExpect(jsonPath("$.user.name", is(AuthTestUtil.VALID_USER_EMAIL)));
    }

    @DisplayName("test for creating an invalid blog having both a category and a user")
    @Test
    @WithMockCustomUser
    void testCreate_whenGivenRequestIsInvalid() throws Exception {
        // Setup
        String title = "";
        String content = "";
        String description = "";
        String thumbnail = "";
        String categoryName = "";
        BlogRequest blogRequest = new BlogRequest(title, content, description, thumbnail, categoryName);

        // Run the test
        ResultActions resultActions = mockMvc.perform(post("/blogs")
                .content(objectMapper.writeValueAsString(blogRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Verify the results
        Optional<Blog> blogOptional = blogRepository.findById(1L);
        assertThat(blogOptional.isPresent()).isFalse();

        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg.thumbnail", isA(String.class)))
                .andExpect(jsonPath("$.msg.description", isA(String.class)))
                .andExpect(jsonPath("$.msg.category", isA(String.class)))
                .andExpect(jsonPath("$.msg.title", isA(String.class)))
                .andExpect(jsonPath("$.msg.content", isA(String.class)));
    }

    @DisplayName("test for creating a blog from an unauthorized user")
    @Test
    void testCreate_whenHavingNoAuthentication() throws Exception {
        // Setup
        String title = "spring boot";
        String content = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.";
        String description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        String thumbnail = "http://www.example.com/thumbnail";
        String categoryName = category.getName();
        BlogRequest blogRequest = new BlogRequest(title, content, description, thumbnail, categoryName);

        // Run the test
        ResultActions resultActions = mockMvc.perform(post("/blogs")
                .content(objectMapper.writeValueAsString(blogRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Verify the results
        Optional<Blog> blogOptional = blogRepository.findById(1L);
        assertThat(blogOptional.isPresent()).isFalse();

        resultActions.andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.msg", is("유효한 자격 증명이 없습니다.")));
    }

    @DisplayName("test for creating a blog with invalid category")
    @Test
    @WithMockCustomUser
    void testCreate_whenGivenCategoryDoesNotExist() throws Exception {
        // Setup
        String title = "spring boot";
        String content = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.";
        String description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        String thumbnail = "http://www.example.com/thumbnail";
        String categoryName = "NOT_EXIST";
        BlogRequest blogRequest = new BlogRequest(title, content, description, thumbnail, categoryName);

        // Run the test
        ResultActions resultActions = mockMvc.perform(post("/blogs")
                .content(objectMapper.writeValueAsString(blogRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Verify the results
        Optional<Blog> blogOptional = blogRepository.findById(1L);
        assertThat(blogOptional.isPresent()).isFalse();

        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg", is("존재하지 않는 카테고리입니다.")));
    }
}
