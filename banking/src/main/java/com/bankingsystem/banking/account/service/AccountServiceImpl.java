package com.bankingsystem.banking.account.service;

import com.bankingsystem.banking.account.DTO.AccountResponse;
import com.bankingsystem.banking.account.repository.AccountRepository;
import com.bankingsystem.banking.account.repository.domain.Account;
import com.bankingsystem.banking.bankname.repository.domain.BankName;
import com.bankingsystem.banking.bankname.service.BankNameService;
import com.bankingsystem.banking.member.DTO.MemberBasic;
import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.product.repository.Product;
import com.bankingsystem.banking.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    /**
     * <pre>
     * Repository를 Service로 바꿔야함.
     * Account 서비스 계층에서 Product DAO 계층을 직접 호출하는 것은 지양해야함.
     *
     * why? Product Entity와 관련한 추가적인 비즈니스 로직이 추가될 수 있음.
     * 따라서, Product의 비즈니스 로직을 다루는 ProducrService를 호출하는 것이 적합함.
     * </pre>
     */
    @Autowired
    ProductRepository productRepository;


    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BankNameService bankNameService;


    // =================== PUBLIC ======================

    @Override
    public Account createAccount(MemberBasic member, String bankId, int productId){
        String accountNum;

        do {
            accountNum = generateAccountNum(productId,bankId);
        }while (existAccountNum(accountNum));

        Product product = productRepository.findById(productId).orElseThrow();
        BankName bankName = bankNameService.findByBankId(bankId);
        Account account = new Account(accountNum,member.toEntity(),bankName,product);
        return saveAccount(account);
    }

    @Override
    public Account saveAccount(Account account){
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
        saveAccount(account);
    }

    @Override
    public Account findByAccountId(String id){
        return accountRepository.findById(id).orElseThrow();
    }

    @Override
    public List<AccountResponse> findAllByMemberBasic(MemberBasic memberRequest){
        Member member = memberRequest.toEntity();
        List<Account> accounts = accountRepository.findAllByMember(member);
        return accounts.stream().map(AccountResponse::of).collect(Collectors.toList());
    }


    @Override
    public void updateBalance(Account account, Long balance){
        account.setBalance(balance);
        saveAccount(account);
    }

    @Override
    public void setLock(Account account){
        account.setLock();
        saveAccount(account);
    }

    @Override
    public void setUnLock(Account account){
        account.setUnLock();
        saveAccount(account);
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
            saveAccount(account);
        }
    }

    @Override
    public boolean canWithDraw(Account account, Long amount){

        Account foundAccount = accountRepository.findById(account.getAccountNum()).orElseThrow();
        if(foundAccount.getBalance() >= amount){
            return true;
        }
        return false;
    }


    // =================== PRIVATE ======================

    private boolean existAccountNum(String accountNum){
        return accountRepository.existsById(accountNum);
    }


    private String generateAccountNum(int productId, String bankId){
        String productIdPart = String.format("%03d",productId);
        String bankIdPart = bankId;

        StringBuilder accountNum = new StringBuilder();
        accountNum.append(productIdPart)
                .append(bankIdPart)
                .append(generateRandomString());

        return accountNum.toString();
    }

    private String generateRandomString(){
        return String.format("%6d",(int)(Math.random()*999999) + 1)
                .replace(" ","0");
    }

}
