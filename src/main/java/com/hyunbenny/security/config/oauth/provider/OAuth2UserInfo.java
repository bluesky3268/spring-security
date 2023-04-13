package com.hyunbenny.security.config.oauth.provider;

// OAuth2.0 제공자들 마다 응답해주는 속성값이 다르기때문에 인터페이스를 만들어서 구현하는 방식을 사용하자.
public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
