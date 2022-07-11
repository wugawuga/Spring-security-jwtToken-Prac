package com.login.jwt.jwtLoginTest.domain;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDTO {

    @NotBlank(message = "아이디를 확인해주세요")
    private String memberId;

    @NotBlank(message = "비밀번호를 확인해주세요")
    private String password;

    public MemberDTO(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    public MemberDTO() {
    }
}
