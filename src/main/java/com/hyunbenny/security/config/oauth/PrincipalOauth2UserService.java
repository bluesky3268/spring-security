package com.hyunbenny.security.config.oauth;

import com.hyunbenny.security.auth.PrincipalDetails;
import com.hyunbenny.security.domain.User;
import com.hyunbenny.security.exception.UserExistException;
import com.hyunbenny.security.repository.UserRepository;
import com.hyunbenny.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    /**
     * loadUser메서드가 종료 될 때 @AuthenticationPrincipal가 생성된다.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        /**
         * 구굴로부터 userRequest를 받아서 후처리를 하는 로직이 들어오는 곳인데
         *
         * userRequest에 들어오는 데이터를 보면 크게 아래와 같다
         *
         * ClientRegistration : ClientRegistration{registrationId='google', clientId='78976879633-s4r0343j7l52dt0vqabrt4g3bdlq74f4.apps.googleusercontent.com', clientSecret='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', clientAuthenticationMethod=org.springframework.security.oauth2.core.ClientAuthenticationMethod@4fcef9d3, authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3, redirectUri='{baseUrl}/{action}/oauth2/code/{registrationId}', scopes=[email, profile], providerDetails=org.springframework.security.oauth2.client.registration.ClientRegistration$ProviderDetails@529fed7b, clientName='Google'}
         * AccessToken : ya29.a0Ael9sCNcKR5dt_YAqmjDThf4tlMrue6w8dnNWWwTiAnyvrbC8tcRpjGicc2OH_0CyhQwK8IFK4MMopYlKwRYxJhZoqXe28q-V6EmNwgXAOzCEQxcsWZhW7oAAEBVLmgwM0z6sX56S7clzOku_B_M659gZcgNaCgYKAS0SARISFQF4udJhOff42ryW5nbmBk1fTCatzQ0163
         * ClientRegistration : ClientRegistration{registrationId='google', clientId='78976879633-s4r0343j7l52dt0vqabrt4g3bdlq74f4.apps.googleusercontent.com', clientSecret='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', clientAuthenticationMethod=org.springframework.security.oauth2.core.ClientAuthenticationMethod@4fcef9d3, authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3, redirectUri='{baseUrl}/{action}/oauth2/code/{registrationId}', scopes=[email, profile], providerDetails=org.springframework.security.oauth2.client.registration.ClientRegistration$ProviderDetails@529fed7b, clientName='Google'}
         * loadUser(userRequest).getAttributes() : {sub=105005781748145736689, name=HYUNBIN CHO, given_name=HYUNBIN, family_name=CHO, picture=https://lh3.googleusercontent.com/a/AGNmyxbK9PGNQ7tonNlaGqSHYWcHuZulwilxRYYQwLF2=s96-c, email=bluesky3268@gmail.com, email_verified=true, locale=ko}
         *
         * 내가 필요한 것은 엑세스 토큰이 아니라 loadUser(userRequest).getAttributes()에 있는 사용자 정보들이다..
         * 저 데이터를 가지고 어떻게 자동으로 회원가입을 시킬거냐면
         *     userId -> {registraionId}_{sub}
         *     password -> "{서버만 아는 키}"를 암호화
         *     email -> {email}
         *     name -> {name}
         *     role -> ROLE_USER
         *     provider : {clientName}
         *     providerId : {sub}
         */

        // Oauth 로그인 시
        OAuth2User oAuth2User = super.loadUser(userRequest);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String userId = provider + "_" + providerId;
        String password = passwordEncoder.encode("hyunbennyServerSecretKey");
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");;
        String role = "ROLE_USER";

        User user = User.of(userId, password, email, name, role, provider, providerId);
        Optional<User> findUser = userRepository.findById(user.getUserId());

        if (findUser.isEmpty()) {
            log.info(">>> first login from {}", provider);
            userRepository.save(user);
        } else {
            log.info(">>> already joined");
            log.info(">>> processing login : {}", userId);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
