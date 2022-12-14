package com.young.blogusbackend.controller;

import com.young.blogusbackend.dto.CommentCreateRequest;
import com.young.blogusbackend.dto.CommentResponse;
import com.young.blogusbackend.dto.CommentUpdateRequest;
import com.young.blogusbackend.dto.GenericResponse;
import com.young.blogusbackend.model.Bloger;
import com.young.blogusbackend.service.CommentService;
import com.young.blogusbackend.util.CurrentBloger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(@Valid @RequestBody CommentCreateRequest createRequest, @CurrentBloger Bloger currentBloger) {
        return commentService.createComment(createRequest, currentBloger);
    }

    @GetMapping("/blog/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getCommentsByBlogId(@PathVariable Long id) {
        return commentService.getCommentsByBlogId(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse updateComment(
            @PathVariable Long id,
            @RequestBody CommentUpdateRequest updateRequest,
            @CurrentBloger Bloger currentBloger
    ) {
        return commentService.updateComment(id, updateRequest, currentBloger);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse deleteComment(@PathVariable Long id, @CurrentBloger Bloger currentBloger) {
        return commentService.deleteComment(id, currentBloger);
    }
}
