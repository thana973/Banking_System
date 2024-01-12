package com.bankingsystem.banking.account.DTO;

import lombok.Getter;

@Getter
public class AccountCreateRequest {
    private final String bankId;
    private final String productId;

    public AccountCreateRequest(String bankId, String productId){
        this.bankId =bankId;
        this.productId = productId;
    }
}
