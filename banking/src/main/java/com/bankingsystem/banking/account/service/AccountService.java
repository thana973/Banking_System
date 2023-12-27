package com.bankingsystem.banking.account.service;

import com.bankingsystem.banking.account.repository.AccountRepository;
import com.bankingsystem.banking.account.repository.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    // =================== PUBLIC ======================

    public Account createAccount(String memberId, String bankName, int productId){
        String accountNum;

        do {
            accountNum = generateAccountNum(productId,bankName);
        }while (existAccountNum(accountNum));

        Account account = new Account(accountNum,memberId,bankName,productId);
        return accountRepository.save(account);
    }

    public Account updateBalance(Account account, Long balance){
        account.setBalance(balance);
        return accountRepository.save(account);
    }

    public boolean canWithDraw(Account account, Long amount){
        return false;
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

}
