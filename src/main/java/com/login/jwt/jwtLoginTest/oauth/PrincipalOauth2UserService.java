package com.login.jwt.jwtLoginTest.oauth;

import com.login.jwt.jwtLoginTest.auth.PrincipalDetails;
import com.login.jwt.jwtLoginTest.domain.UserJWT;
import com.login.jwt.jwtLoginTest.repository.UserJWTRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserJWTRepository userJWTRepository;

    // 구글로 받은 userRequest 데이터 후처리 메서드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("구글로 받은 userRequest 데이터 후처리 메서드");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 구글로그인하면 강제 회원가입
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER";

        UserJWT userEntity = userJWTRepository.findByUsername(username);

        if (userEntity == null) {
            userEntity = UserJWT.builder()
                .email(email)
                .username(username)
                .roles(role)
                .provider(provider)
                .providerId(providerId)
                .build();
            userJWTRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
