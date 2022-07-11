package com.login.jwt.jwtLoginTest.auth;

import com.login.jwt.jwtLoginTest.domain.User;
import com.login.jwt.jwtLoginTest.domain.UserJWT;
import com.login.jwt.jwtLoginTest.repository.UserJWTRepository;
import com.login.jwt.jwtLoginTest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// security login
// loginProcessingUrl("login")
// login 요청이 오면 자동으로 UserDetailsService 타입으로 IOC 되어 있는
// loadUserByUsername 함수가 실행됩니당

// http://localhost:8080/login
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserJWTRepository userJWTRepository;

    // 시큐리티 세션(내부 Authentication(내부 UserDetails))
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        UserJWT userEntity = userRepository.findByUsername(username);
//        if (userEntity != null) {
//            return new PrincipalDetails(userEntity);
//        }
//        return null;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserJWT userEntity = userJWTRepository.findByUsername(username);
        System.out.println(userEntity);
        return new PrincipalDetails(userEntity);
    }
}
