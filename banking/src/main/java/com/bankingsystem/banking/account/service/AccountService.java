package com.bankingsystem.banking.account.service;

import com.bankingsystem.banking.account.DTO.AccountResponse;
import com.bankingsystem.banking.account.repository.domain.Account;
import com.bankingsystem.banking.member.DTO.MemberBasic;

import java.util.List;

public interface AccountService {
    Account createAccount(MemberBasic memberBasic, String bankName, int productId);

    Account saveAccount(Account account);

    void deleteAccountList(String[] accountNumList);

    void deleteAccountBy(String accountNum);

    void deposit(String accountNum, Long amount);

    Account findByAccountId(String id);

    List<AccountResponse> findAllByMemberBasic(MemberBasic memberRequest);

    void updateBalance(Account account, Long balance);

    void setLock(Account account);

    void setUnLock(Account account);

    boolean isLocked(Account account);

    void withDraw(String accountNum, Long amount);

    boolean canWithDraw(Account account, Long amount);
}
