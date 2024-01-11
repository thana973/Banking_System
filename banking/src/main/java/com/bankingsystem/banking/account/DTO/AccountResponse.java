package com.bankingsystem.banking.account.DTO;


import com.bankingsystem.banking.account.repository.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AccountResponse {
    private final String accountNum;
    private final String bankName;
    private final boolean locked;
    private final Long balance;
    private final String productName;


    public static AccountResponse of(Account account){
        return AccountResponse.builder()
                .accountNum(account.getAccountNum())
                .bankName(account.getBankName())
                .locked(account.isLocked())
                .balance(account.getBalance())
                .productName(account.getProductName())
                .build();
    }
}
