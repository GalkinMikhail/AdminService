package com.example.adminservice.service.interfaces;

import com.example.adminservice.dto.TokenResponse;
import com.example.adminservice.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    TokenResponse getToken(UserDto userDto);
    List<UserDto> getAllUsers(String token);
}
