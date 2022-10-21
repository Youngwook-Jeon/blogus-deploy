package com.young.blogusbackend.service;

import com.young.blogusbackend.dto.BlogerResponse;
import com.young.blogusbackend.dto.ResetPasswordRequest;
import com.young.blogusbackend.dto.UpdateBlogerRequest;
import com.young.blogusbackend.exception.SpringBlogusException;
import com.young.blogusbackend.mapper.BlogerMapper;
import com.young.blogusbackend.model.Bloger;
import com.young.blogusbackend.repository.BlogerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogerService {

    private final BlogerRepository blogerRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlogerMapper blogerMapper;

    public void updateUserProfile(UpdateBlogerRequest updateBlogerRequest, Bloger currentBloger) {
        currentBloger.setName(updateBlogerRequest.getName());
        currentBloger.setAvatar(updateBlogerRequest.getAvatar());
        currentBloger.setUpdatedAt(Instant.now());
        blogerRepository.save(currentBloger);
    }

    public void resetPassword(ResetPasswordRequest passwordRequest, Bloger currentBloger) {
        currentBloger.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        blogerRepository.save(currentBloger);
    }


    public BlogerResponse getUserById(Long id) {
        Bloger bloger = blogerRepository.findById(id)
                .orElseThrow(() -> new SpringBlogusException("존재하지 않는 유저입니다."));
        return blogerMapper.blogerToBlogerResponse(bloger);
    }
}
