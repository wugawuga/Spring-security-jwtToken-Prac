package com.login.jwt.jwtLoginTest.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.jwt.jwtLoginTest.auth.PrincipalDetails;
import com.login.jwt.jwtLoginTest.domain.UserJWT;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 스프링 시큐리티에 UsernamePasswordAuthenticationFilter 가 있다
// '/login' username, password 전송하면(post)
// 필터 동작함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    // '/login' 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도 중");

        // 1. username, password 받기
        try {
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while ((input = br.readLine()) != null) {
//                System.out.println(input);
//            }
            ObjectMapper om = new ObjectMapper();
            UserJWT userJWT = om.readValue(request.getInputStream(), UserJWT.class);
            System.out.println(userJWT);

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                    userJWT.getUsername(),
                    userJWT.getPassword()
                );
            // PrincipalDetailsService 호출 loadUserByUsername() 함수 실행
            Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("1=================================");
            System.out.println(principalDetails.getUserJWT().getUsername());

            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면
    // successfulAuthentication 실행
    //
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨 : 로그인 인증 완료");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // RSA 방식 ㄴㄴ
        // HMAC 방식
        String jwtToken = JWT.create()
            .withSubject("cos토큰")
            .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 1)))
            .withClaim("id", principalDetails.getUserJWT().getId())
            .withClaim("username", principalDetails.getUserJWT().getUsername())
            .sign(Algorithm.HMAC512("cos"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
