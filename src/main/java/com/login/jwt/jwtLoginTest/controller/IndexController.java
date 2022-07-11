package com.login.jwt.jwtLoginTest.controller;

import static com.login.jwt.jwtLoginTest.domain.User.createUser;

import com.login.jwt.jwtLoginTest.domain.User;
import com.login.jwt.jwtLoginTest.domain.UserDTO;
import com.login.jwt.jwtLoginTest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"/", ""})
    public String index() {

        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

//    @PostMapping("/join")
//    public String join(UserDTO userDTO) {
//        System.out.println(userDTO);
//        User user = changeUser(userDTO);
//        userRepository.save(user);
//        return "redirect:/loginForm";
//    }

    private User changeUser(UserDTO userDTO) {
        return createUser(
            userDTO.getUsername(),
            bCryptPasswordEncoder.encode(userDTO.getPassword()),
            userDTO.getEmail(),
            "ROLE_USER"
        );
    }

    @GetMapping("/info")
    @Secured("ROLE_ADMIN")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @GetMapping("data")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public @ResponseBody String data() {
        return "데이터정보";
    }

}
