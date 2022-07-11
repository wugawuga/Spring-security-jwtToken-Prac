package com.login.jwt.jwtLoginTest.controller;

import static com.login.jwt.jwtLoginTest.domain.UserJWT.createUserJWT;

import com.login.jwt.jwtLoginTest.domain.UserDTO;
import com.login.jwt.jwtLoginTest.domain.UserJWT;
import com.login.jwt.jwtLoginTest.repository.UserJWTRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserJWTRepository userJWTRepository;

    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token() {
        return "<h1>token</h1>";
    }

    @PostMapping("/join")
    public String join(@RequestBody UserDTO userDTO) {
        UserJWT userJWT = changeUser(userDTO);
        userJWTRepository.save(userJWT);
        return "회원가입완료";
    }

    private UserJWT changeUser(UserDTO userDTO) {
        return createUserJWT(
            userDTO.getUsername(),
            bCryptPasswordEncoder.encode(userDTO.getPassword()),
            "ROLE_USER"
        );
    }

    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }

    @GetMapping("/api/v1/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/api/v1/admin")
    public String admin() {
        return "admin";
    }
}
