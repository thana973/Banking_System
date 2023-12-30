package com.bankingsystem.banking.account.controller;

import com.bankingsystem.banking.account.repository.domain.Account;
import com.bankingsystem.banking.account.service.AccountService;
import com.bankingsystem.banking.account.service.AccountServiceImpl;
import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/index")
    public String index(){
        return "account/index";
    }

    @GetMapping("/list")
    public String getAccountsByMemberName(@RequestParam(name = "username") String username, HttpServletRequest request){
        // member는 임시로 가져온 것. 추후 제거 필요.
        Member member = memberRepository.findByName(username);

        List<Account> accounts = accountService.findAllBy(member);
        HttpSession session = request.getSession();
        session.setAttribute("member",member);
        session.setAttribute("accounts",accounts);
        System.out.println("df");
        return "account/list";
    }

    @GetMapping("/create")
    public String getCreateAccount(){
        return "account/create";
    }

    @PostMapping("/create")
    public String createAccount(@RequestParam("bankName") String bankName, @RequestParam("productId") String productId,HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute("member");
        if(bankName != null && !productId.isEmpty())
            accountService.createAccount(member,bankName,Integer.parseInt(productId));
        return "redirect:list?username=" + member.getName();
    }

    @GetMapping("/delete")
    public String getDeletePage(){
        return "account/delete";
    }

    @PostMapping("/delete")
    public String deleteAccount(HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute("member");

        String[] accountNums = request.getParameterValues("accountNum");
        if(accountNums.length > 0)
            accountService.deleteAccountList(accountNums);
        return "redirect:list?username=" + member.getName();
    }

    @GetMapping("/deposit")
    public String getDepositPage(){
        return "account/deposit";
    }

    @PostMapping("/deposit")
    public String depositAccount(HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute("member");
        String accountNum = request.getParameter("accountNum");
        Long amount = Long.parseLong(request.getParameter("amount"));

        if (accountNum != null && amount > 0)
            accountService.deposit(accountNum,amount);
        return "redirect:list?username=" + member.getName();
    }

    @GetMapping("/withdraw")
    public String getWithDrawPage(){
        return "account/withdraw";
    }

    @PostMapping("/withdraw")
    public String withDrawAccount(HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute("member");
        String accountNum = request.getParameter("accountNum");
        Long amount = Long.parseLong(request.getParameter("amount"));

        if (accountNum != null && amount > 0)
            accountService.withDraw(accountNum,amount);
        return "redirect:list?username=" + member.getName();
    }

    @GetMapping("/transfer")
    public String getTransferPage(){
        return "account/transfer";
    }

    @PostMapping("/transfer")
    public String transferAccount(HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute("member");
        String fromAccountNum = request.getParameter("fromAccountNum");
        String toAccountNum = request.getParameter("toAccountNum");
        Long amount = Long.parseLong(request.getParameter("amount"));

        if (fromAccountNum != null && toAccountNum != null && amount > 0)
            accountService.transfer(fromAccountNum,toAccountNum,amount);
        return "redirect:list?username=" + member.getName();
    }

}
