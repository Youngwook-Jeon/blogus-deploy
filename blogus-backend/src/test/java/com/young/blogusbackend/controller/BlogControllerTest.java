package com.young.blogusbackend.controller;

import com.young.blogusbackend.model.Blog;
import com.young.blogusbackend.model.Bloger;
import com.young.blogusbackend.model.Category;
import com.young.blogusbackend.repository.BlogRepository;
import com.young.blogusbackend.repository.BlogerRepository;
import com.young.blogusbackend.repository.CategoryRepository;
import com.young.blogusbackend.service.MailService;
import com.young.blogusbackend.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
public class BlogControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogerRepository blogerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @MockBean
    MailService mailService;

    void initSearching() {
        Bloger bloger = blogerRepository.save(AuthTestUtil.createValidUser());
        Category category = categoryRepository.save(CategoryTestUtil.createValidCategory());

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
}
