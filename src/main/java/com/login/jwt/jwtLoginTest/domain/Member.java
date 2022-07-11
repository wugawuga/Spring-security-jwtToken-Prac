package com.login.jwt.jwtLoginTest.domain;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String memberId;
    private String password;

    private Member(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    public static Member createMember(MemberDTO memberDTO) {

        return new Member(memberDTO.getMemberId(), memberDTO.getPassword());
    }
}
