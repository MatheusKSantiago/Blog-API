package com.learn.blog.config.security;

import com.learn.blog.user.UserLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO user){
        var auth = new UsernamePasswordAuthenticationToken(user.email(),user.password());
        //authenticate returns 401 UNAUTHORIZED if the credentials are wrong
        Authentication authentication = authenticationManager.authenticate(auth);

        return ResponseEntity.ok(tokenService.getToken(user.email()));
    }

    @GetMapping("/temp")
    public void temp(){

    }
}
