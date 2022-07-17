package com.login.jwt.jwtLoginTest.auth;

import com.login.jwt.jwtLoginTest.domain.User;
import com.login.jwt.jwtLoginTest.domain.UserJWT;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private UserJWT userJWT;
    private Map<String, Object> attributes;

    public PrincipalDetails(UserJWT userJWT) {
        this.userJWT = userJWT;
    }

    public PrincipalDetails(UserJWT userJWT, Map<String, Object> attributes) {
        this.userJWT = userJWT;
        this.attributes = attributes;
    }

    // 해당 유저의 권한 반환!! - security login
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add((GrantedAuthority) () -> user.getRole());
//        return collect;
//    }

    // jwt login
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        userJWT.getRoleList().forEach(r ->
            collect.add(() -> r)
        );
        return collect;
    }

    @Override
    public String getPassword() {
        return userJWT.getPassword();
    }

    @Override
    public String getUsername() {
        return userJWT.getUsername();
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

        // 1년동안 유저가 로그인을 안하면! 휴면 계정으로
        // user.getLoginTime
        // 현재시간 - 로그인시간 => 1년 넘어가면 false 반환
       return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
