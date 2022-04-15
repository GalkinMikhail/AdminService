package com.example.adminservice.service.implement;

import com.example.adminservice.dto.TokenResponse;
import com.example.adminservice.dto.UserDto;
import com.example.adminservice.exception.AccessDeniedException;
import com.example.adminservice.exception.BadCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceImplTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getToken() {
        UserDto userDto = new UserDto(1L,"admin","admin");

        TokenResponse result = adminService.getToken(userDto);

        assertNotNull(result.getUsername());
        assertNotNull(result.getToken());
    }

    @Test
    void getAllUsers() {
        UserDto userDto = new UserDto(1L,"admin","admin");
        TokenResponse tokenResponse = adminService.getToken(userDto);
        List<UserDto> userList = adminService.getAllUsers(tokenResponse.getToken());

        assertNotNull(userList);
        System.out.println(userList);
    }

    @Test
    void throwsBadCredentialsExceptionTest(){
        UserDto userDto = new UserDto(1L,"||||","||||");

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> adminService.getToken(userDto));

        assertInstanceOf(BadCredentialsException.class,exception);
    }

    @Test
    void throwsAccessDeniedException(){
        UserDto userDto = new UserDto(1L,"user","user");
        TokenResponse tokenResponse = adminService.getToken(userDto);

        assertNotNull(tokenResponse.getToken());
        assertNotNull(tokenResponse.getUsername());

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> adminService.getAllUsers(tokenResponse.getToken()));
        assertInstanceOf(AccessDeniedException.class,exception);
    }
}