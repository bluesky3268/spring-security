package com.hyunbenny.security.controller;

import com.hyunbenny.security.dto.request.JoinRequest;
import com.hyunbenny.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    @GetMapping({"", "/"})
    public String index() {
        // mustache의 기본 폴더 설정 : src/main/resources
        // 그러므로 view resolver 설정 시 prefix : templates, suffix : .mustache
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

    @GetMapping("/login-form")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public @ResponseBody String login() {
        return "login";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String join(JoinRequest request) {
        userService.join(request.toUserDto());
        return "redirect:/login-form";
    }
}
