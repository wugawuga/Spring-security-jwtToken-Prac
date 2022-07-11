package com.login.jwt.jwtLoginTest.controller;

import com.login.jwt.jwtLoginTest.domain.Member;
import com.login.jwt.jwtLoginTest.domain.MemberDTO;
import com.login.jwt.jwtLoginTest.service.MemberService;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/notJwtLogin")
    public String mainPage(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "Main";
    }

    @PostMapping("/notJwtLogin")
    public String login(@Valid MemberDTO memberDTO, BindingResult result, HttpSession session)
        throws NoSuchAlgorithmException {
        if (result.hasErrors()) {
            return "Main";
        }
        MemberDTO memberDTO1 = memberService.checkId(memberDTO);
        session.setAttribute("user", memberDTO1);
        return "LoginSuccess";
    }

    @PostMapping("/members/new")
    public String loginCheck(@Valid MemberDTO memberDTO, BindingResult result)
        throws NoSuchAlgorithmException {

        if (result.hasErrors()) {
            return "member/New";
        }
        Member join = memberService.join(memberDTO);

        return "Success";
    }

    @GetMapping("/members/new")
    public String memberJoin(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "/member/New";
    }
}
