package com.young.blogusbackend.service;

import com.young.blogusbackend.dto.CommentCreateRequest;
import com.young.blogusbackend.dto.CommentResponse;
import com.young.blogusbackend.dto.CommentUpdateRequest;
import com.young.blogusbackend.exception.SpringBlogusException;
import com.young.blogusbackend.mapper.CommentMapper;
import com.young.blogusbackend.model.Blog;
import com.young.blogusbackend.model.Bloger;
import com.young.blogusbackend.model.Comment;
import com.young.blogusbackend.repository.BlogRepository;
import com.young.blogusbackend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    public static final String DELETED_CONTENT = "삭제된 댓글입니다.";
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentResponse createComment(CommentCreateRequest createRequest, Bloger currentBloger) {
        Blog blog = blogRepository.findById(createRequest.getBlogId())
                .orElseThrow(() -> new SpringBlogusException("존재하지 않는 블로그입니다."));
        Comment comment = commentMapper.commentCreateRequestToComment(createRequest, currentBloger, blog);
        commentRepository.save(comment);

        return commentMapper.commentToCommentResponse(comment);
    }

    public List<CommentResponse> getCommentsByBlogId(Long blogId) {
        List<Comment> commentList = commentRepository.findAllByBlogIdOrderByCreatedAt(blogId);
        return commentMapper.commentListToCommentResponseList(commentList);
    }

    public CommentResponse updateComment(Long id, CommentUpdateRequest updateRequest, Bloger currentBloger) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new SpringBlogusException("존재하지 않는 댓글입니다."));
        if (!Objects.equals(currentBloger.getId(), comment.getBloger().getId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        if (comment.getIsDeleted()) {
            throw new SpringBlogusException("이미 삭제된 댓글입니다.");
        }

        comment.setContent(updateRequest.getContent());
        comment.setUpdatedAt(Instant.now());
        commentRepository.save(comment);

        return commentMapper.commentToCommentResponse(comment);
    }

    public CommentResponse deleteComment(Long id, Bloger currentBloger) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new SpringBlogusException("존재하지 않는 댓글입니다."));
        if (!(comment.getBlogUserId().equals(currentBloger.getId()) ||
                comment.getBloger().getId().equals(currentBloger.getId()))) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        if (comment.getIsDeleted()) {
            throw new SpringBlogusException("이미 삭제된 댓글입니다.");
        }

        comment.setIsDeleted(true);
        comment.setContent(DELETED_CONTENT);
        comment.setUpdatedAt(Instant.now());
        commentRepository.save(comment);

        return commentMapper.commentToCommentResponse(comment);
    }
}
