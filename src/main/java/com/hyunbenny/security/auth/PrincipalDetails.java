package com.hyunbenny.security.auth;

import com.hyunbenny.security.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 시큐리티 설정에서 loginProcessingUrl("/login")을 설정하면
 * 시큐리티가 해당 요청에 대해서 중간에 낚아채서 로그인을 진행하는데
 *
 * 로그인이 완료가 되면 시큐리티 Session(SecurityContextHolder)을 만들어서 정보를 저장한다.
 *
 * SecurityContextHolder에는 Authentication타입의 객체만 저장할 수 있다.
 *
 * Authentication 객체는 회원 정보를 가지고 있는 UserDetails타입으로 캡슐화(?) 되어 있다고 보면 되겠다.
 *
 */
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    // 일반 로그인
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth 로그인
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    ////////// UserDetails //////////
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /**
         * 해당 유저의 `권한`을 반환한다.
         */
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRoles());
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    ////////// OAuth2User //////////
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return attributes.get("sub").toString();
    }
}
