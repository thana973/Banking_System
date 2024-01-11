package com.bankingsystem.banking.transaction.controller;

import com.bankingsystem.banking.member.DTO.MemberBasic;
import com.bankingsystem.banking.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transfer")
    public String getTransferPage(){
        return "transaction/transfer";
    }

    @PostMapping("/transfer")
    public String transferAccount(HttpServletRequest request){
        MemberBasic member = (MemberBasic) request.getSession().getAttribute("member");
        String fromAccountNum = request.getParameter("fromAccountNum");
        String toAccountNum = request.getParameter("toAccountNum");
        long amount = Long.parseLong(request.getParameter("amount"));

        if (fromAccountNum != null && toAccountNum != null && amount > 0)
            transactionService.transfer(fromAccountNum,toAccountNum,amount);
        return "redirect:../account/list?username=" + member.getName();
    }
}
