package com.bankingsystem.banking.bankname.repository;

import com.bankingsystem.banking.bankname.repository.domain.BankName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankNameRepository extends JpaRepository<BankName, String> {
}
