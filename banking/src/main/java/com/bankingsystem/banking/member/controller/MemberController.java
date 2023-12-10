package com.bankingsystem.banking.member.controller;

import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.member.dto.MemberDto;
import com.bankingsystem.banking.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;
    @GetMapping("/joinUp")
    public String joinUpPage() {
        return "member/joinUp";
    }
    @GetMapping("/completed")
    public String completedPage() {
        return "member/completed";
    }
    @PostMapping("/joinUp")
    @ResponseBody
    public String joinUp(MemberDto dto, HttpSession session){
        String confirmNumber = dto.getMailConfirmNumber();
        Integer expectedConfirmationCode = (Integer) session.getAttribute("confirmationCode");
        if (expectedConfirmationCode.toString().equals(confirmNumber)) {
            if(!memberService.joinUp(dto)){
                return "joinUpFailed";
            }
        } else {
            return "emailVerificationFailed";
        }

        return "success";
    }

    @ResponseBody
    @PostMapping("/isIdDuplicate")
    public boolean isIdDuplicate(String memberId){
        log.info("MemberController.isIdDuplicate");

        return memberService.isIdDuplicate(memberId);
    }

}
