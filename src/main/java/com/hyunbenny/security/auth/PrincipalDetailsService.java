package com.hyunbenny.security.auth;

import com.hyunbenny.security.domain.User;
import com.hyunbenny.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Authentication객체 만들기
 * 시큐리티 설정에서 loginProcessingUrl("/login")을 설정하면
 * 시큐리티가 해당 요청에 대해서 중간에 낚아채서 로그인을 진행하는데
 * 이 때 UserDetailsService의 loadUserByUsername()가 호출된다.
 * -> 여기서는 내가 PrincipalDetailsService에서 UserDetailsService를 구현해놨으니까 PrincipalDetailsService의 loadUserByUsername()가 호출되겠지
 *
 * 로그인할 때 넘겨주는 파라미터의 이름이 username이어야 한다.
 * userId나 user_name 이런거로 하면 매칭이 안된다.
 * 정 다른 파라미터 명을 사용하고 싶다면 SecurityConfig에서 usernameParameter를 따로 설정해줘야 한다.
 */

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 여기서 반환하는
     * UserDetails타입의 객체는 Authentication으로 반환되고
     * Authentication은 SecurityContextHolder로 들어간다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        return new PrincipalDetails(user);
    }
}
