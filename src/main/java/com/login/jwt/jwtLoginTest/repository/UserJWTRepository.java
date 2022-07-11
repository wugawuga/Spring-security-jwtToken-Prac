package com.login.jwt.jwtLoginTest.repository;

import com.login.jwt.jwtLoginTest.domain.UserJWT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJWTRepository extends JpaRepository<UserJWT, Long> {

    UserJWT findByUsername(String username);
}
