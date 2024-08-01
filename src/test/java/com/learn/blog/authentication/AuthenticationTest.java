package com.learn.blog.authentication;

import com.learn.blog.BlogApplication;
import com.learn.blog.config.security.SecurityConfig;
import com.learn.blog.config.security.TokenService;
import com.learn.blog.user.User;
import com.learn.blog.user.UserLoginDTO;
import com.learn.blog.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {
    @LocalServerPort
    private int port;
    private TestRestTemplate rest;
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private TokenService tokenService;

    @Autowired
    public AuthenticationTest(TestRestTemplate rest,
                              UserRepository userRepository,
                              PasswordEncoder encoder,
                              TokenService tokenService)
    {

        this.rest = rest;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }


    @Test
    @DisplayName("you give valid credentials to login (OK, token not null)")
    public void loginSucess(){
        var user = new User();
        user.setRole("MANAGER");
        user.setEmail("testU");
        user.setPassword(encoder.encode("123"));
        userRepository.save(user);

        var dto = new UserLoginDTO(user.getEmail(), "123");

        var headers = new HttpHeaders();
        headers.add("Content-Type","application/json");

        HttpEntity<UserLoginDTO> request = new HttpEntity<>(dto,headers);


        ResponseEntity<String> response = rest.postForEntity("/auth/login",
                request,String.class);


        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(tokenService.verify(response.getBody()));
    }

    @Test
    @DisplayName("you give invalid credentials to login (Unauthorized, token null)")
    public void loginUnauthorized(){
        var user = new User();
        user.setRole("MANAGER");
        user.setEmail("test2");
        user.setPassword(encoder.encode("123"));
        userRepository.save(user);

        var dto = new UserLoginDTO(user.getEmail(), "1234");

        var headers = new HttpHeaders();
        headers.add("Content-Type","application/json");

        HttpEntity<UserLoginDTO> request = new HttpEntity<>(dto,headers);


        ResponseEntity<String> response = rest.postForEntity("/auth/login",
                request,String.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        Assertions.assertNull(tokenService.verify(response.getBody()));
    }

    @Test
    @DisplayName("endpoint requires you to be authenticated (and you are)")
    public void youNeedToBeAuthenticatedS(){
        var user = new User();
        user.setRole("MANAGER");
        user.setEmail("test3");
        user.setPassword(encoder.encode("123"));
        userRepository.save(user);


        var headers = new HttpHeaders();
        String authorization = "Bearer " + tokenService.getToken(user.getEmail());
        headers.add("Authorization",authorization);
        var request = new HttpEntity<Void>(headers);

        ResponseEntity<Void> response = rest.exchange("/auth/temp",HttpMethod.GET,request,Void.class);


        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    @DisplayName("endpoint requires you to be authenticated (and you are not)")
    public void youNeedToBeAuthenticatedF(){
        ResponseEntity<Void> response = rest.getForEntity("/auth/temp",Void.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
    }
}
