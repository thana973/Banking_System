package com.bankingsystem.banking.account.controller;

import com.bankingsystem.banking.account.DTO.AccountResponse;
import com.bankingsystem.banking.account.service.AccountService;
import com.bankingsystem.banking.bankname.DTO.BankNameResponse;
import com.bankingsystem.banking.bankname.service.BankNameService;
import com.bankingsystem.banking.member.DTO.MemberBasic;
import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.member.repository.MemberRepository;
import com.bankingsystem.banking.transaction.service.TransactionService;
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
    BankNameService bankNameService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TransactionService transactionService;


    @GetMapping("/index")
    public String index(){
        return "account/index";
    }

    @GetMapping("/list")
    public String getAccountsByMemberName(@RequestParam(name = "username") String username, HttpServletRequest request){
        // member는 임시로 가져온 것. 추후 제거 필요.
        Member memberEntity = memberRepository.findByName(username);

        MemberBasic member = MemberBasic.of(memberEntity);

        List<AccountResponse> accounts = accountService.findAllByMemberBasic(member);
        HttpSession session = request.getSession();

        session.setAttribute("member",member);
        session.setAttribute("accounts",accounts);

        return "account/list";
    }

    @GetMapping("/create")
    public String getCreateAccount(HttpServletRequest request){
        List<BankNameResponse> bankNameResponseList = bankNameService.getBankNameResponseList();
        request.setAttribute("bankNameList",bankNameResponseList);
        return "account/create";
    }

    @PostMapping("/create")
    public String createAccount(@RequestParam("bankId") String bankId, @RequestParam("productId") String productId,HttpServletRequest request){

        MemberBasic member = (MemberBasic) request.getSession().getAttribute("member");

        if(bankId != null && productId != null)
            accountService.createAccount(member,bankId,Integer.parseInt(productId));
        return "redirect:list?username=" + member.getName();
    }

    @GetMapping("/delete")
    public String getDeletePage(){
        return "account/delete";
    }

    @PostMapping("/delete")
    public String deleteAccount(HttpServletRequest request){
        MemberBasic member = (MemberBasic) request.getSession().getAttribute("member");

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
        MemberBasic member = (MemberBasic) request.getSession().getAttribute("member");
        String accountNum = request.getParameter("accountNum");
        long amount = Long.parseLong(request.getParameter("amount"));

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
        MemberBasic member = (MemberBasic) request.getSession().getAttribute("member");
        String accountNum = request.getParameter("accountNum");
        long amount = Long.parseLong(request.getParameter("amount"));
        if (accountNum != null && amount > 0)
            accountService.withDraw(accountNum,amount);
        return "redirect:list?username=" + member.getName();
    }

}
