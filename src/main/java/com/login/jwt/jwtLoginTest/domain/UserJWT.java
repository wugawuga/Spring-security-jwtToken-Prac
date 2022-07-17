package com.login.jwt.jwtLoginTest.domain;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Data
public class UserJWT {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String roles; // USER, ADMIN

    private String provider;
    private String providerId;

    @CreatedDate
    private LocalDateTime createDate;

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    private UserJWT(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Builder
    public UserJWT(String username, String email, String password, String roles,
        String provider, String providerId, LocalDateTime createDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }

    public static UserJWT createUserJWT(String username, String password, String roles) {
        return new UserJWT(
            username,
            password,
            roles
        );
    }
}
