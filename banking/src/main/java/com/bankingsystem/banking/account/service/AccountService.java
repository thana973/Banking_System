package com.bankingsystem.banking.account.service;

import com.bankingsystem.banking.account.repository.domain.Account;
import com.bankingsystem.banking.member.domain.Member;

import java.util.List;

public interface AccountService {
    Account createAccount(Member member, String bankName, int productId);

    void deleteAccountList(String[] accountNumList);

    void deleteAccountBy(String accountNum);

    void deposit(String accountNum, Long amount);

    List<Account> findAllBy(Member member);

    void updateBalance(Account account, Long balance);

    void setLock(Account account);

    void setUnLock(Account account);

    boolean isLocked(Account account);

    void withDraw(String accountNum, Long amount);

    void transfer(String fromAccountNum, String toAccountNum, Long amount);
}
