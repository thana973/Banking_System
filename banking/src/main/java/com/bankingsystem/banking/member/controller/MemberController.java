package com.bankingsystem.banking.member.controller;

import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.member.dto.MemberDto;
import com.bankingsystem.banking.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;
    @GetMapping("/joinUp")
    public String joinUpPage() {
        return "joinUp";
    }
    @PostMapping("/joinUp")
    public String joinUp(MemberDto dto){
        memberService.joinUp(dto);
        return "hi";
    }

}
