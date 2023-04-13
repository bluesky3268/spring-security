package com.hyunbenny.security.config;

import com.hyunbenny.security.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true) // deprecated되어 아래의 어노테이션으로 대체
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // -> @Secured 활성화, @PreAuthorize 활성화 시키는 어노테이션
@RequiredArgsConstructor
public class SecurityConfig {

    public final PrincipalOauth2UserService principalOauth2UserService;

    /**
     * spring boot 2.7 부터는
     * 기존 WebSecurityConfigurerAdapter를 상속받아서 하는 방법이 아니라
     * Bean Component 방식으로 바뀜.
     * -> 빈으로 등록해서 사용해야 한다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.csrf().disable();

         http
                .authorizeHttpRequests()
                    .requestMatchers("/user/**").authenticated()
                    .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login-form")
    //                .usernameParameter("userId")
                    .loginProcessingUrl("/login") // '/login' 이 호출되면 스프링 시큐리티가 중간에 낚아채서 대신 로그인을 진행해준다. 그렇기 때문에 내가 직접 로그인을 구현할 필요가 없다.
                    .defaultSuccessUrl("/") // 로그인이 성공하면 이동할 주소
                .and()
                // oauth 로그인 url설정
                .oauth2Login().loginPage("/login-form")
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService);

        /**
         * oauth 로그인 처리 과정
         * 1. 코드를 받는다(인증)
         * 2. 엑세스 토큰을 가지고(권한)
         * 3. 사용자 프로필 정보를 가져와서
         * 4. 회원가입을 자동으로 진행하거나 웹페이지의 회원가입을 시킬 수도 있다(주소 등 추가 정보가 필요한 경우)
         *
         * 1 ~ 3은 따로 코드가 필요하지 않다.
         */
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
