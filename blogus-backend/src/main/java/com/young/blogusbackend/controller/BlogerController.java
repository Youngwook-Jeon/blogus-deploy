package com.young.blogusbackend.controller;

import com.young.blogusbackend.dto.BlogerResponse;
import com.young.blogusbackend.dto.GenericResponse;
import com.young.blogusbackend.dto.ResetPasswordRequest;
import com.young.blogusbackend.dto.UpdateBlogerRequest;
import com.young.blogusbackend.model.Bloger;
import com.young.blogusbackend.service.BlogerService;
import com.young.blogusbackend.util.CurrentBloger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users") @RequiredArgsConstructor
public class BlogerController {

    private final BlogerService blogerService;

    @PatchMapping("/update_profile")
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse updateUserProfile(
            @Valid @RequestBody UpdateBlogerRequest updateBlogerRequest,
            @CurrentBloger Bloger currentBloger
    ) {
        blogerService.updateUserProfile(updateBlogerRequest, currentBloger);
        return new GenericResponse("유저 정보가 업데이트되었습니다.");
    }

    @PatchMapping("/reset_password")
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse resetPassword(
            @Valid @RequestBody ResetPasswordRequest passwordRequest,
            @CurrentBloger Bloger currentBloger
    ) {
        blogerService.resetPassword(passwordRequest, currentBloger);
        return new GenericResponse("비밀번호가 변경되었습니다.");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BlogerResponse getUserById(@PathVariable Long id) {
        return blogerService.getUserById(id);
    }
}
