package com.example.adminservice.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseControllerAdvice {
    Date date = new Date();
    @ExceptionHandler
    public Object badCredentialsException(BadCredentialsException ex){
        return response(HttpStatus.UNAUTHORIZED,ex);
    }
    @ExceptionHandler
    public Object AccessDeniedException(AccessDeniedException ex){
        return response(HttpStatus.UNAUTHORIZED,ex);
    }

    private Object response(HttpStatus status, AbstractException ex){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String,String> body = new HashMap<>();
        body.put("message",ex.getMessage());
        body.put("techInfo", ex.getTechInfo());
        body.put("status",status.toString());
        body.put("timestamp",String.valueOf(date));
        return new ResponseEntity<>(body,headers,status);
    }
}
