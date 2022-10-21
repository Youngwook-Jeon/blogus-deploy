package com.young.blogusbackend.util;

import com.young.blogusbackend.model.Bloger;
import com.young.blogusbackend.security.BlogerAccount;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        final Bloger bloger = AuthTestUtil.createValidUser();
        bloger.setName(annotation.username());
        final User user = new BlogerAccount(bloger, true, true, true);

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );
        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
