package com.bankingsystem.banking.bankname.DTO;

import com.bankingsystem.banking.bankname.repository.domain.BankName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@Builder
public class BankNameResponse implements Serializable {
    private static final long serialVersionUID = 4240634232152739911L;

    private final String id;
    private final String name;

    public static BankNameResponse of(BankName bankName){
        return BankNameResponse.builder().name(bankName.getName())
                .id(bankName.getId())
                .build();
    }
}
