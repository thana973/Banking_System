package com.bankingsystem.banking.acount;

import com.bankingsystem.banking.account.repository.AccountRepository;
import com.bankingsystem.banking.account.repository.domain.Account;
import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;



import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
public class TestAccountRepository {

    private Member member;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp(){
        accountRepository.deleteAll();
        member = Member.builder()
                .name("name")
                .password("1234")
                .authId(1)
                .build();
        memberRepository.save(member);
        Account sample1 = new Account("11112333331",member,"bank1",1);
        Account sample2 = new Account("11112333332",member,"bank2",2);
        Account sample3 = new Account("11112333333",member,"bank3",3);
        accountRepository.saveAll(Arrays.asList(sample1,sample2,sample3));
    }

    @Test
    void findByAccountNum(){
        Account account = accountRepository.findById("11112333331").orElseThrow();
        assertEquals("bank1",account.getBankName());
    }

    @Test
    void update(){
        Account sample1 = new Account("11112333331",member,"bank1",1);
        sample1.setBalance(12L);
        accountRepository.save(sample1);
        assertEquals(12L,accountRepository.findById(sample1.getAccountNum()).get().getBalance());
    }

    @Test
    void getAccountByMemberId(){
        List<Account> accounts = accountRepository.findAllByMember(member);
        assertEquals(3,accounts.size());
    }
}
