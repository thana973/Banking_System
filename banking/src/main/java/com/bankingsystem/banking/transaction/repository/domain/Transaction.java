package com.bankingsystem.banking.transaction.repository.domain;


import com.bankingsystem.banking.account.repository.domain.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_NUM", insertable = false, nullable = false)
    private int transactionNum;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_DATE" , insertable = false)
    private LocalDateTime date;

    @Column(name = "TRANSACTION_AMOUNT")
    private Long amount;

    @Column(name = "TRANSACTION_FROM")
    private String from;

    @Column(name = "TRANSACTION_TO")
    private String to;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ACCOUNT_NUM")
    private Account account;

    @Builder
    public Transaction(Long amount, String from, String to, Account account){
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.account = account;
    }
}
