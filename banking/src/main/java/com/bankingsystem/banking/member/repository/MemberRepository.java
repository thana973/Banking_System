package com.bankingsystem.banking.member.repository;

import com.bankingsystem.banking.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}