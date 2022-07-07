package com.blogservice.controller;

import com.blogservice.exception.IllegalPostPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.blogservice.controller")
public class ApiExceptionController {

    @ExceptionHandler
    public ResponseEntity<String> illegalArgExHandler(IllegalPostPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body("비밀번호가 일치하지 않거나 없는 게시물입니다.");
    }

    // 요래 해도 된다.
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler
//    public String illegalArgExHandler(IllegalArgumentException e) {
//        return "비밀번호가 일치하지 않거나 없는 게시물입니다.";
//    }
}
