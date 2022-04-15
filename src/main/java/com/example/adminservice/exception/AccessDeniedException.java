package com.example.adminservice.exception;

public class AccessDeniedException extends AbstractException{
    public AccessDeniedException(String message, String techInfo) {
        super(message, techInfo);
    }
}
