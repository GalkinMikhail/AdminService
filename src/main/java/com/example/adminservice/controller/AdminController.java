package com.example.adminservice.controller;


import com.example.adminservice.dto.TokenResponse;
import com.example.adminservice.dto.UserDto;
import com.example.adminservice.service.interfaces.AdminService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserDto userDto){
        return ResponseEntity.ok(adminService.getToken(userDto));
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam String token){
        return ResponseEntity.ok(adminService.getAllUsers(token));
    }
}
