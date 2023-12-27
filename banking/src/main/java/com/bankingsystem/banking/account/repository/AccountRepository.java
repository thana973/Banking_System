package com.bankingsystem.banking.account.repository;

import com.bankingsystem.banking.account.repository.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}
