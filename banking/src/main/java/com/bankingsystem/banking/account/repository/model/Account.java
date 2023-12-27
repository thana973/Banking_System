package com.bankingsystem.banking.account.repository.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {

    /**
     * 계좌번호 = 계좌종류(세 자릿수) + 은행번호(한 자릿수) + 랜덤값(여섯 자릿수)
     * 계좌종류 : DEPOSIT(001) , CD(002)
     * 은행번호 : A은행(1), B은행(2) <- 임시 값
     */

    @Id
    @Column(name = "ACCOUNT_NUM",length = 15,nullable = false)
    private String accountNum;

    // MEMBER 테이블과 매핑 필요
    @Column(name = "MEMBER_ID",length = 10,nullable = false)
    private String memberId;

    @Column(name = "BANK_NAME",length = 20)
    private String bankName;

    @Column(name = "STATUS")
    private boolean restricted;

    @Column(name = "BALANCE")
    private Long balance;

    // PRODUCT 테이블과 매핑 필요
    @Column(name = "PRODUCT_PRODUCT_ID",nullable = false)
    private int productId;

    public Account(String accountNum, String memberId, String bankName, int productId){
        this.accountNum = accountNum;
        this.memberId = memberId;
        this.bankName = bankName;
        this.restricted = false;
        this.balance = 0L;
        this.productId = productId;
    }



}
