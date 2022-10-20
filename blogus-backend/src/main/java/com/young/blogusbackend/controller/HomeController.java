package com.young.blogusbackend.controller;

import com.young.blogusbackend.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse home() {
        return new GenericResponse("서버가 동작중입니다.");
    }

    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GenericResponse error() {
        return new GenericResponse("존재하지 않는 경로입니다.");
    }
}
