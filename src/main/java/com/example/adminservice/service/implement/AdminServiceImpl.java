package com.example.adminservice.service.implement;

import com.example.adminservice.dto.TokenResponse;
import com.example.adminservice.dto.UserDto;
import com.example.adminservice.exception.AccessDeniedException;
import com.example.adminservice.exception.BadCredentialsException;
import com.example.adminservice.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${USER_SERVICE_AUTH_URL:http://localhost:8080/auth/login}")
    private String AUTH_URL;

    @Value("${USER_SERVICE_GET_URL:http://localhost:8080/api/user}")
    private String GET_URL;

    @Override
    public TokenResponse getToken(UserDto userDto) {
        try {
            JSONObject request = new JSONObject();
            request.put("username", userDto.getUsername());
            request.put("password", userDto.getPassword());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

            ResponseEntity<TokenResponse> loginResponse = restTemplate.exchange(AUTH_URL, HttpMethod.POST, entity, TokenResponse.class);

            return loginResponse.getBody();
        }
        catch (HttpClientErrorException e){
            throw new BadCredentialsException("Invalid username or password","username/pass");
        }
    }

    @Override
    public List<UserDto> getAllUsers(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer_" + token);
            HttpEntity<List<UserDto>> entity = new HttpEntity<>(headers);
            ResponseEntity<List<UserDto>> response = restTemplate.exchange(GET_URL, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });
            return response.getBody();
        }
        catch (HttpClientErrorException e){
            throw new AccessDeniedException("Access denied","");
        }
    }
}
