package com.bankingsystem.banking.account.repository;

import com.bankingsystem.banking.account.repository.domain.Account;
import com.bankingsystem.banking.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findAllByMember(Member member);
}
