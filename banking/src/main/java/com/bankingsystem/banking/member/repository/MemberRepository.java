package com.bankingsystem.banking.member.repository;


import com.bankingsystem.banking.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByIdentification(String identification);

    boolean existsBymemberId(String member_id);
}