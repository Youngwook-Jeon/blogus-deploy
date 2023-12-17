package com.young.blogusbackend.util;

import com.young.blogusbackend.model.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default AuthTestUtil.VALID_USER_EMAIL;

    Role role() default Role.ROLE_USER;
}
