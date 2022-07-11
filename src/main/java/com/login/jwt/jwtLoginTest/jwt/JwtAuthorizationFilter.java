package com.login.jwt.jwtLoginTest.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.login.jwt.jwtLoginTest.auth.PrincipalDetails;
import com.login.jwt.jwtLoginTest.domain.UserJWT;
import com.login.jwt.jwtLoginTest.repository.UserJWTRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// security filter 가지고 있는데 그 필터 중에
// BasicAuthenticationFilter 있다
// 권한이나 인증이 필요한 특정 주소를 요청하면 위 필터를 거치게 된다
// 만약에 권한이나 인증이 필요한 주소가 아니면 이 필터를 거치지 않는다
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserJWTRepository userJWTRepository;
    public JwtAuthorizationFilter(
        AuthenticationManager authenticationManager,
        UserJWTRepository userJWTRepository) {
        super(authenticationManager);
        this.userJWTRepository = userJWTRepository;
    }

    // 인증 권한 필요한 주소요청이 있을 때 이 필터 거침
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이 권한이 필요한 주소가 요청 됨");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader = " + jwtHeader);

        // header 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰 검증해서 정상적인 유저인지 확인
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");

        String username = JWT.require(Algorithm.HMAC512("cos")).build()
            .verify(jwtToken)
            .getClaim("username")
            .asString();

        // 서명이 정상적으로 됐을 때
        if (username != null) {
            UserJWT userEntity = userJWTRepository.findByUsername(username);

            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            // JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만듬
            Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalDetails, null,
                    principalDetails.getAuthorities());

            // 강제로 security 세션에 접근해서 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }
}
