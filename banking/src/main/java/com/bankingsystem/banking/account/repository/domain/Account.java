package com.bankingsystem.banking.account.repository.domain;


import com.bankingsystem.banking.bankname.repository.domain.BankName;
import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.product.repository.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name = "ACCOUNT")
public class Account {

    /**
     * <pre>
     * 계좌번호 = 계좌종류(세 자릿수) + 은행번호(한 자릿수) + 랜덤값(여섯 자릿수)
     *
     * -------- 예시 ---------
     * 계좌종류 : DEPOSIT(001) , CD(002)
     * 은행번호 : A은행(1), B은행(2)
     */
    @Id
    @Column(name = "ACCOUNT_NUM",length = 15,nullable = false)
    private String accountNum;

    // MEMBER 테이블과 매핑 필요
    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name="BANK_NAME_ID")
    private BankName bankName;

    @Column(name = "STATUS")
    private boolean locked;

    @Setter
    @Column(name = "BALANCE")
    private Long balance;

    // PRODUCT 테이블과 매핑 필요
    @ManyToOne
    @JoinColumn(name = "PRODUCT_PRODUCT_ID")
    private Product product;

    /**
     * 이 생성자를 사용하지 않고, AccountService의 saveAccount 메서드 사용하도록 함.
     */
    public Account(String accountNum, Member member, BankName bankName, Product product){
        this.accountNum = accountNum;
        this.member = member;
        this.bankName = bankName;
        this.locked = false;
        this.balance = 0L;
        this.product = product;
    }

    public void setLock(){
        this.locked = true;
    }

    public void setUnLock(){
        this.locked = false;
    }

    public void addBalance(Long amount){
        this.balance += amount;
    }

    public void subtractBalance(Long amount){
        this.balance -= amount;
    }

    public String getProductName() {
        return product.getProductName();
    }

    public String getBankName(){return bankName.getName();}
}
