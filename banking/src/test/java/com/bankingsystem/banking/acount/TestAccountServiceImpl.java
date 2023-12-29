package com.bankingsystem.banking.acount;

import com.bankingsystem.banking.account.repository.AccountRepository;
import com.bankingsystem.banking.account.repository.domain.Account;
import com.bankingsystem.banking.account.service.AccountServiceImpl;
import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
public class TestAccountServiceImpl {

    @Autowired
    AccountServiceImpl accountServiceImpl;
    @Autowired
    AccountRepository accountRepository;

    private static Member member1;
    private static Member member2;

    @BeforeAll
    public static void init(@Autowired MemberRepository memberRepository){
        member1 = Member.builder()
                .name("John")
                .password("1234")
                .authId(1)
                .build();
        member2 = Member.builder()
                .name("John")
                .password("1234")
                .authId(1)
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @BeforeEach
    void beforeEach(){
        accountRepository.deleteAll();
    }

    @Test
    void saveAccount_Then_Create_UniqueAccountNum(){
        String bankName = "A은행";
        int productId = 1;
        Account createdAccount = accountServiceImpl.createAccount(member1,bankName,productId);
        Optional<Account> foundAccount = accountRepository.findById(createdAccount.getAccountNum());
        assertTrue(foundAccount.isPresent());
    }

    @Test
    void saveAccountMany_Then_CountMany(){
        String bankName = "A은행";
        int productId = 1;

        for(int i = 0; i < 10; i++){
            accountServiceImpl.createAccount(member1,bankName,productId);
        }
        assertEquals(10,accountRepository.findAll().size());
    }

    @Test
    void updateBalance(){
        Account account = new Account(
                "0011000001",
                member1,
                "B은행",
                1
        );
        Account createdAccount = accountRepository.save(account);
        accountServiceImpl.updateBalance(createdAccount,1000L);
        Account foundAccount = accountRepository.findById(createdAccount.getAccountNum()).get();
        assertEquals(createdAccount.getAccountNum(),foundAccount.getAccountNum());
        assertEquals(1000L,foundAccount.getBalance());
    }

    @Test
    void testSetLock(){
        Account createdAccount = accountServiceImpl.createAccount(member1,"A은행",1);
        accountServiceImpl.setLock(createdAccount);

        assertTrue(createdAccount.isLocked());
    }

    @Test
    void testSetUnLock(){
        Account createdAccount = accountServiceImpl.createAccount(member1,"A은행",1);
        accountServiceImpl.setLock(createdAccount);
        accountServiceImpl.setUnLock(createdAccount);

        assertFalse(createdAccount.isLocked());
    }

    @Test
    void testIsLocked(){
        Account createdAccount = accountServiceImpl.createAccount(member1,"A은행",1);

        assertFalse(accountServiceImpl.isLocked(createdAccount));
    }

    @Test
    void findAllByMember(){
        accountServiceImpl.createAccount(member1,"A은행",1);
        accountServiceImpl.createAccount(member1,"B은행",1);

        List<Account> accountList1 =  accountServiceImpl.findAllBy(member1);
        List<Account> accountList2 =  accountServiceImpl.findAllBy(member2);

        assertEquals(2,accountList1.size());
        assertTrue(accountList2.isEmpty());
    }
}
