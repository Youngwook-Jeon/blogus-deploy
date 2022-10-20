package com.young.blogusbackend.exception;

import com.young.blogusbackend.dto.ErrorResponse;
import com.young.blogusbackend.dto.ValidationErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler implements ErrorController {

    public static final String ACCESS_NOT_PERMITED = "접근 권한이 없습니다.";
    public static final String NOT_FOUND = "이 URL에 대응되는 리소스가 존재하지 않습니다.";
    public static final String METHOD_NOT_ALLOWED = "해당 엔드포인트에 허용되는 메서드가 아닙니다.";
    public static final String INTERNAL_SERVER_ERROR = "서버 에러가 발생했습니다. 잠시 후에 다시 시도해주세요.";
    public static final String ERROR_PATH = "/error";

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest webRequest
    ) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            if (error instanceof FieldError) {
                name = ((FieldError) error).getField();
            }

            errors.put(name, errorMessage);
        }

        ValidationErrors validationErrors = new ValidationErrors(errors);
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest webRequest) {
        return new ResponseEntity<>(getErrorResponse(ACCESS_NOT_PERMITED), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = { SpringBlogusException.class })
    public ResponseEntity<Object> handleSpringBlogusException(SpringBlogusException ex, WebRequest webRequest) {
        return new ResponseEntity<>(getErrorResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest webRequest) {
        return new ResponseEntity<>(getErrorResponse(NOT_FOUND), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<Object> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest webRequest) {
        return new ResponseEntity<>(getErrorResponse(METHOD_NOT_ALLOWED), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleException(Exception ex, WebRequest webRequest) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(getErrorResponse(INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<Object> notFound404() {
        return new ResponseEntity<>(getErrorResponse(NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    private ErrorResponse getErrorResponse(Exception ex) {
        return new ErrorResponse(ex.getMessage());
    }

    private ErrorResponse getErrorResponse(String msg) {
        return new ErrorResponse(msg);
    }

}
