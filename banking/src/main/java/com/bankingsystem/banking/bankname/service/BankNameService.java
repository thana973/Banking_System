package com.bankingsystem.banking.bankname.service;

import com.bankingsystem.banking.bankname.DTO.BankNameResponse;
import com.bankingsystem.banking.bankname.repository.BankNameRepository;
import com.bankingsystem.banking.bankname.repository.domain.BankName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BankNameService {

    @Autowired
    BankNameRepository bankNameRepository;

    public List<BankNameResponse> getBankNameResponseList(){
        List<BankName> bankNameList = bankNameRepository.findAll();
        return bankNameList.stream().map(BankNameResponse::of).collect(Collectors.toList());
    }

    public BankName findByBankId(String id){
        return bankNameRepository.findById(id).orElseThrow();
    }

}
