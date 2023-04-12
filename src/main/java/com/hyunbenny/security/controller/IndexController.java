package com.hyunbenny.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"", "/"})
    public String index() {
        // mustache의 기본 폴더 설정 : src/main/resources
        // 그러므로 view resolver 설정 시 prefix : templates, suffix : .mustache
        return "index";
    }
}
