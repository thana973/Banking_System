package com.bankingsystem.banking.bankname.DTO;

import com.bankingsystem.banking.bankname.repository.domain.BankName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BankNameResponse {

    private final String id;
    private final String name;

    public static BankNameResponse of(BankName bankName){
        return BankNameResponse.builder().name(bankName.getName())
                .id(bankName.getId())
                .build();
    }
}
