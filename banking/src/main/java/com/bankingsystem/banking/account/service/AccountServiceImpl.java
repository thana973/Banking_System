package com.bankingsystem.banking.account.service;

import com.bankingsystem.banking.account.repository.AccountRepository;
import com.bankingsystem.banking.account.repository.domain.Account;
import com.bankingsystem.banking.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    // =================== PUBLIC ======================

    @Override
    public Account createAccount(Member member, String bankName, int productId){
        String accountNum;

        do {
            accountNum = generateAccountNum(productId,bankName);
        }while (existAccountNum(accountNum));

        Account account = new Account(accountNum,member,bankName,productId);
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccountList(String[] accountNumList){
        for (String accountNum : accountNumList) {
            deleteAccountBy(accountNum);
        }
    }

    @Override
    public void deleteAccountBy(String accountNum){
        accountRepository.deleteById(accountNum);
    }

    @Override
    public void deposit(String accountNum, Long amount){
        Account account = accountRepository.findById(accountNum).orElseThrow();
        account.addBalance(amount);
        accountRepository.save(account);
    }


    @Override
    public List<Account> findAllBy(Member member){
        return accountRepository.findAllByMember(member);
    }


    @Override
    public void updateBalance(Account account, Long balance){
        account.setBalance(balance);
        accountRepository.save(account);
    }

    @Override
    public void setLock(Account account){
        account.setLock();
        accountRepository.save(account);
    }

    @Override
    public void setUnLock(Account account){
        account.setUnLock();
        accountRepository.save(account);
    }

    @Override
    public boolean isLocked(Account account){
        return account.isLocked();
    }

    @Override
    public void withDraw(String accountNum, Long amount){
        Account account = accountRepository.findById(accountNum).orElseThrow();
        if(canWithDraw(account,amount)){
            account.subtractBalance(amount);
            accountRepository.save(account);
        }
    }

    @Override
    public void transfer(String fromAccountNum, String toAccountNum, Long amount){
        Account fromAccount = accountRepository.findById(fromAccountNum).orElseThrow();
        Account toAccount = accountRepository.findById(toAccountNum).orElseThrow();

        if(amount > 0 && canWithDraw(fromAccount,amount)){
            fromAccount.subtractBalance(amount);
            toAccount.addBalance(amount);
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
        }

    }



    // =================== PRIVATE ======================

    private boolean existAccountNum(String accountNum){
        return accountRepository.existsById(accountNum);
    }


    private String generateAccountNum(int productId, String bankName){
        StringBuilder accountNum = new StringBuilder();
        switch (productId){
            case 1:
                accountNum.append("001");
                break;
            case 2:
                accountNum.append("002");
                break;
        }

        switch (bankName){
            case "A은행":
                accountNum.append("1");
                break;
            case "B은행":
                accountNum.append("2");
                break;
            default:
                throw new RuntimeException("No Exist BankName. Check out BankName List");
        }
        accountNum.append(generateRandomString());
        return accountNum.toString();
    }

    private String generateRandomString(){
        return String.format("%6d",(int)(Math.random()*999999) + 1)
                .replace(" ","0");
    }

    private boolean canWithDraw(Account account, Long amount){

        Account foundAccount = accountRepository.findById(account.getAccountNum()).orElseThrow();
        if(foundAccount.getBalance() >= amount){
            return true;
        }
        return false;
    }

}
