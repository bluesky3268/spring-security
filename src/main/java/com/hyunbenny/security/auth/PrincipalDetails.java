package com.hyunbenny.security.auth;

import com.hyunbenny.security.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

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
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    /**
     * 해당 유저의 권한을 반환한다.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
}
