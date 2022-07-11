package com.login.jwt.jwtLoginTest.service;

import static com.login.jwt.jwtLoginTest.domain.Member.*;
import static com.login.jwt.jwtLoginTest.jwt.SHA256.encrypt;

import com.login.jwt.jwtLoginTest.domain.Member;
import com.login.jwt.jwtLoginTest.domain.MemberDTO;
import com.login.jwt.jwtLoginTest.repository.MemberRepository;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(MemberDTO memberDTO) throws NoSuchAlgorithmException {

        validateDuplicateMember(memberDTO);
        Member member = changeDTO(memberDTO);
        memberRepository.save(member);

        return member;
    }

    private void validateDuplicateMember(MemberDTO memberDTO) {
        Member findMembers = memberRepository.findByMemberId(memberDTO.getMemberId());
        if (findMembers != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    private Member changeDTO(MemberDTO memberDTO) throws NoSuchAlgorithmException {

        memberDTO.setPassword(encrypt(memberDTO.getPassword()));
        return createMember(memberDTO);
    }

    public MemberDTO checkId(MemberDTO memberDTO) throws NoSuchAlgorithmException {

        Member byMemberId = memberRepository.findByMemberId(memberDTO.getMemberId());
        if (byMemberId == null) {
            throw new IllegalStateException("존재하는 회원이 없어요");
        }
        log.info("dto.password - {} ", encrypt(memberDTO.getPassword()));
        log.info("byId.password - {} ", byMemberId.getPassword());
        if (encrypt(memberDTO.getPassword()).equals(byMemberId.getPassword())) {
            return memberDTO;
        } else {
            throw new IllegalArgumentException("비밀번호 틀려요");
        }
    }
}
