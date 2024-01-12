package com.bankingsystem.banking.account.controller;

import com.bankingsystem.banking.account.DTO.AccountCreateRequest;
import com.bankingsystem.banking.account.DTO.AccountResponse;
import com.bankingsystem.banking.account.service.AccountService;
import com.bankingsystem.banking.bankname.DTO.BankNameResponse;
import com.bankingsystem.banking.bankname.service.BankNameService;
import com.bankingsystem.banking.member.DTO.MemberBasic;
import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/account")
public class AccountController {

    ///////////////////
    //               //
    //     FIELD     //
    //               //
    ///////////////////

    @Autowired
    private AccountService accountService;

    @Autowired
    private BankNameService bankNameService;

    @Autowired
    private MemberRepository memberRepository;

    private final String MEMBER_PARAM = "member";
    private final String ACCOUNT_NUMBER = "accountNum";
    private final String ACCOUNT_RESPONSE_LIST = "accounts";
    private final String ACCOUNT_AMOUNT = "amount";



    ///////////////////
    //               //
    //  GET METHOD   //
    //               //
    ///////////////////

    @GetMapping("/index")
    public String index(){
        return "account/index";
    }

    @GetMapping("/list")
    public String getAccountsByMemberName(@RequestParam(name = "username") String username, HttpServletRequest request){
        // Member Entity 는 임시로 사용 중. 추후 MemberService 에서 DTO 넘겨받아야 됨.
        Member memberEntity = memberRepository.findByName(username);

        MemberBasic member = MemberBasic.of(memberEntity);

        ArrayList<AccountResponse> accounts = new ArrayList<>(accountService.findAllByMemberBasic(member));
        HttpSession session = request.getSession();

        session.setAttribute(MEMBER_PARAM,member);
        session.setAttribute(ACCOUNT_RESPONSE_LIST,accounts);

        return "account/list";
    }

    @GetMapping("/create")
    public String getCreateAccount(HttpServletRequest request){
        ArrayList<BankNameResponse> bankNameResponseList = new ArrayList<>(bankNameService.getBankNameResponseList());
        request.setAttribute("bankNameList",bankNameResponseList);
        return "account/create";
    }


    @GetMapping("/delete")
    public String getDeletePage(){
        return "account/delete";
    }

    @GetMapping("/deposit")
    public String getDepositPage(){
        return "account/deposit";
    }

    @GetMapping("/withdraw")
    public String getWithDrawPage(){
        return "account/withdraw";
    }



    ///////////////////
    //               //
    //  POST METHOD  //
    //               //
    ///////////////////


    @PostMapping("/create")
    public String createAccount(AccountCreateRequest accountCreateRequest, HttpServletRequest request){
        MemberBasic member = (MemberBasic) request.getSession().getAttribute(MEMBER_PARAM);

        String bankId = accountCreateRequest.getBankId();
        String productId = accountCreateRequest.getProductId();

        accountService.createAccount(member,bankId,Integer.parseInt(productId));
        return "redirect:list?username=" + member.getName();
    }

    @PostMapping("/delete")
    public String deleteAccount(HttpServletRequest request){
        MemberBasic member = (MemberBasic) request.getSession().getAttribute(MEMBER_PARAM);

        String[] accountNums = request.getParameterValues(ACCOUNT_NUMBER);
        if(accountNums.length > 0)
            accountService.deleteAccountList(accountNums);
        return "redirect:list?username=" + member.getName();
    }

    @PostMapping("/deposit")
    public String depositAccount(HttpServletRequest request){
        MemberBasic member = (MemberBasic) request.getSession().getAttribute(MEMBER_PARAM);

        String accountNum = request.getParameter(ACCOUNT_NUMBER);
        long amount = Long.parseLong(request.getParameter(ACCOUNT_AMOUNT));

        if (accountNum != null && amount > 0)
            accountService.deposit(accountNum,amount);
        return "redirect:list?username=" + member.getName();
    }

    @PostMapping("/withdraw")
    public String withDrawAccount(HttpServletRequest request){
        MemberBasic member = (MemberBasic) request.getSession().getAttribute(MEMBER_PARAM);

        String accountNum = request.getParameter(ACCOUNT_NUMBER);
        long amount = Long.parseLong(request.getParameter(ACCOUNT_AMOUNT));

        if (accountNum != null && amount > 0)
            accountService.withDraw(accountNum,amount);
        return "redirect:list?username=" + member.getName();
    }

}
