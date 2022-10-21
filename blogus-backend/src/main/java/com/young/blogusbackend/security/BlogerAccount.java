package com.young.blogusbackend.security;

import com.young.blogusbackend.model.Bloger;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class BlogerAccount extends User {

    private Bloger bloger;

    public BlogerAccount(Bloger bloger, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {
        super(bloger.getEmail(), bloger.getPassword(), bloger.isEnabled(),
                accountNonExpired, credentialsNonExpired, accountNonLocked,
                Collections.singletonList(new SimpleGrantedAuthority(bloger.getRole().name())));
        this.bloger = bloger;
    }
}
