package com.login.jwt.jwtLoginTest.repository;

import com.login.jwt.jwtLoginTest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
