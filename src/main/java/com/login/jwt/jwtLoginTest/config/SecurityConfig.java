package com.login.jwt.jwtLoginTest.config;

import com.login.jwt.jwtLoginTest.auth.PrincipalDetails;
import com.login.jwt.jwtLoginTest.auth.PrincipalDetailsService;
import com.login.jwt.jwtLoginTest.filter.MyFilter1;
import com.login.jwt.jwtLoginTest.filter.MyFilter3;
import com.login.jwt.jwtLoginTest.jwt.JwtAuthenticationFilter;
import com.login.jwt.jwtLoginTest.jwt.JwtAuthorizationFilter;
import com.login.jwt.jwtLoginTest.oauth.PrincipalOauth2UserService;
import com.login.jwt.jwtLoginTest.repository.UserJWTRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
            .antMatchers("/user/**").authenticated()
            .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/loginForm")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/")
            .and()
            .oauth2Login()
            .loginPage("/loginForm")
            .userInfoEndpoint()
            .userService(principalOauth2UserService);
    }

//    private final CorsFilter corsFilter;
//    private final UserJWTRepository userJWTRepository;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .addFilter(corsFilter) // @CrossOrigin(인증x), 시큐리티 필터에 등록 인증(o)
//            .formLogin().disable()
//            .httpBasic().disable()
//            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
//            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userJWTRepository))
//            .authorizeRequests()
//            .antMatchers("/api/v1/user/**")
//            .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//            .antMatchers("/api/v1/manager/**")
//            .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//            .antMatchers("/api/v1/admin/**")
//            .access("hasRole('ROLE_ADMIN')")
//            .antMatchers("/user/**").authenticated()
//            .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
//            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//            .anyRequest().permitAll()
//            .and()
//            .oauth2Login()
//            .loginPage("/loginForm");
//    }

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}
