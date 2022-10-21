package com.young.blogusbackend.service;

import com.young.blogusbackend.model.Bloger;
import com.young.blogusbackend.repository.BlogerRepository;
import com.young.blogusbackend.security.BlogerAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final BlogerRepository blogerRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        Optional<Bloger> blogerOptional = blogerRepository.findByEmail(email);
        Bloger bloger = blogerOptional
                .orElseThrow(() -> new UsernameNotFoundException(email + " 계정을 찾을 수 없습니다."));

        return new BlogerAccount(bloger, true, true, true);
    }

}
