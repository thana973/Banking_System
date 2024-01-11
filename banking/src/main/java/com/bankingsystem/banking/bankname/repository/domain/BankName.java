package com.bankingsystem.banking.bankname.repository.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "BANK_NAME")
public class BankName {

    @Id
    @Column(name = "BANK_NAME_ID", length = 1, nullable = false)
    String id;

    @Column(name = "NAME")
    String name;
}
