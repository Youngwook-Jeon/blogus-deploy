package com.young.blogusbackend.util;

import com.young.blogusbackend.model.Blog;

import java.time.Instant;

public class BlogTestUtil {

    public static final String TITLE = "Test title";
    public static final String DESCRIPTION = "Java Spring React Blog Platform";
    public static final String CONTENT = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.";

    public static Blog createValidBlog() {
        return createValidBlogWithGivenTitleAndDescription(TITLE, DESCRIPTION);
    }

    public static Blog createValidBlogWithGivenTitleAndDescription(String title, String description) {
        return Blog.builder()
                .title(title)
                .description(description)
                .content(CONTENT)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .thumbnail("thumbnail")
                .build();
    }
}
