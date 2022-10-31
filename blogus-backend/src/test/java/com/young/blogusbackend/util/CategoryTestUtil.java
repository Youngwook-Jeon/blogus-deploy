package com.young.blogusbackend.util;

import com.young.blogusbackend.model.Category;

import java.time.Instant;

public class CategoryTestUtil {

    public static final String NAME = "java";

    public static Category createValidCategory() {
        return Category.builder()
                .name(NAME)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
