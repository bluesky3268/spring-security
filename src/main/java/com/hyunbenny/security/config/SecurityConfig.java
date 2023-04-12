package com.hyunbenny.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * spring boot 2.7 부터는
     * 기존 WebSecurityConfigurerAdapter를 상속받아서 하는 방법이 아니라
     * Bean Component 방식으로 바뀜.
     * -> 빈으로 등록해서 사용해야 한다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login-form")
//                .usernameParameter("userId")
                .loginProcessingUrl("/login") // '/login' 이 호출되면 스프링 시큐리티가 중간에 낚아채서 대신 로그인을 진행해준다. 그렇기 때문에 내가 직접 로그인을 구현할 필요가 없다.
                .defaultSuccessUrl("/") // 로그인이 성공하면 이동할 주소
                .and()
                .csrf().disable()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
