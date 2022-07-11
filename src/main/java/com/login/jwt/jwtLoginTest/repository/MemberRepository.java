package com.login.jwt.jwtLoginTest.repository;

import com.login.jwt.jwtLoginTest.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemberId(String memberId);
}
