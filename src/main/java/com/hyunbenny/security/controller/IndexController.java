package com.hyunbenny.security.controller;

import com.hyunbenny.security.auth.PrincipalDetails;
import com.hyunbenny.security.dto.request.JoinRequest;
import com.hyunbenny.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    /**
     * SecuritySession에 들어갈 수 있는 객체 타입은 Authentication 하나 밖에 없다.
     * 근데 Authentication에 들어갈 수 있는 객체 타입은 2개다
     *     - UserDetails : 일반 로그인
     *     - Oauth2User : OAuth 로그인
     *
     * 예시는 아래의 두 api를 보면 된다.('/test/login', '/test/oauth/login')
     * 그러면 인증이 필요한 모든 api2가지를 구분해서 개발하면 너무 불편한데..?
     *
     * -> 객체지향적으로 생각해보자.
     *     >>> PrincipalDetails 는 이미 UserDetails를 구현했다. 그러면 Oauth2User도 같이 구현하면
     *         UserDetails와 Oauth2User를 구분하지 않고 PrincipalDetails 하나로 받아서 쓸 수 있다.
     *
     * Oauth2User를 구현하고 난 뒤 PrincipalOauth2UserService에서 받은 회원정보를 바탕으로 회원가입을 구현해주고,
     * PrincipalDetails을 리턴해주면
     *
     * 우리는 `/user` api와 같이 한번에 두 가지 타입의 인증을 동시에 처리할 수 있게 된다.
     */

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication auth,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[GET] /test/login");
        PrincipalDetails principal = (PrincipalDetails) auth.getPrincipal();
        log.info(">>>>> auth : {}", principal.getUser().toString());
        log.info(">>>>> principalDetails : {}", principalDetails.getUser().toString());
        return "세션 정보 확인";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication auth,
                                          @AuthenticationPrincipal OAuth2User oAuth2) {
        log.info("[GET] /test/oauth/login");
        OAuth2User oAuth2User = (OAuth2User) auth.getPrincipal();
        log.info(">>>>> oAuth2User.getAttributes() : {}", oAuth2User.getAttributes());
        log.info(">>>>> oAuth2.getAttributes() : {}", oAuth2.getAttributes());
        return "세션 정보 확인";
    }

    @GetMapping({"", "/"})
    public String index() {
        // mustache의 기본 폴더 설정 : src/main/resources
        // 그러므로 view resolver 설정 시 prefix : templates, suffix : .mustache
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[GET] /user");
        log.info(">>> principalDetails : {}", principalDetails.getUser());
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

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // data()가 실행되기 직전에 실행된다. 하나만 걸 떄는 @Secured 쓰면 됨
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "DATA";
    }
}
