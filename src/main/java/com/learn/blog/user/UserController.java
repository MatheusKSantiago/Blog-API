package com.learn.blog.user;

import com.learn.blog.config.security.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("user")
public class UserController {

    private TokenService tokenService;

    public UserController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

   @GetMapping("login")
    public void login(@RequestBody UserLoginDTO user,
                                        HttpServletResponse response) throws IOException {
       response.sendRedirect("/auth/login");
   }
}
