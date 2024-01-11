package com.bankingsystem.banking.transaction.service;

import com.bankingsystem.banking.account.repository.domain.Account;
import com.bankingsystem.banking.account.service.AccountService;
import com.bankingsystem.banking.transaction.repository.TransactionRepository;
import com.bankingsystem.banking.transaction.repository.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    AccountService accountService;
    @Autowired
    TransactionRepository transactionRepository;

    /**
     * <pre>
     * 어떤 계좌번호를 외래키로 매핑해야 되는지 결정하지 못하여,
     * 임시로 fromAccountNum을 외래키로 넘겨줌
     *
     * 추후 결정 필요 [24.01.11 - 김정민]
     * </pre>
     */
    public void transfer(String fromAccountNum, String toAccountNum, Long amount){
        Account fromAccount = accountService.findByAccountId(fromAccountNum);
        Account toAccount = accountService.findByAccountId(toAccountNum);
        Transaction transaction = Transaction.builder()
                .from(fromAccountNum)
                .to(toAccountNum)
                .amount(amount)
                .account(fromAccount)
                .build();

        if(amount > 0 && accountService.canWithDraw(fromAccount,amount)){
            fromAccount.subtractBalance(amount);
            toAccount.addBalance(amount);
            transactionRepository.save(transaction);
            accountService.saveAccount(fromAccount);
            accountService.saveAccount(toAccount);
        }
    }
}
