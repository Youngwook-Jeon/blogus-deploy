package com.young.blogusbackend.service;

import com.young.blogusbackend.dto.BlogResponse;
import com.young.blogusbackend.mapper.BlogMapper;
import com.young.blogusbackend.mapper.CategoryMapper;
import com.young.blogusbackend.model.Blog;
import com.young.blogusbackend.repository.BlogRepository;
import com.young.blogusbackend.repository.CategoryRepository;
import com.young.blogusbackend.util.BlogTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

    @Mock
    private BlogRepository mockBlogRepository;

    @Mock
    private CategoryRepository mockCategoryRepository;

    @Mock
    private BlogMapper mockBlogMapper;

    @Mock
    private CategoryMapper mockCategoryMapper;

    @InjectMocks
    private BlogService blogServiceUnderTest;

    @DisplayName("Test the searching functionality")
    @Test
    void testSearch() {
        // Setup
        String keyword = "Java";
        BlogResponse response = BlogResponse.builder()
                .title(BlogTestUtil.TITLE)
                .description(BlogTestUtil.DESCRIPTION)
                .content(BlogTestUtil.CONTENT)
                .build();
        when(mockBlogRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword))
                .thenReturn(List.of(BlogTestUtil.createValidBlog()));
        when(mockBlogMapper.blogListToBlogResponseList(anyList())).thenReturn(List.of(response));

        // Run the test
        List<BlogResponse> responseList = blogServiceUnderTest.getBlogsByKeyword(keyword);

        // Verify the results
        verify(mockBlogRepository, times(1))
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        assertThat(responseList.size()).isEqualTo(1);
        assertThat(responseList.get(0).getDescription().contains(keyword)).isTrue();
    }
}
