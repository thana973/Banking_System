package com.bankingsystem.banking.member.controller;

import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.member.dto.MemberDto;
import com.bankingsystem.banking.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;
    @GetMapping("/joinUp")
    public String joinUpPage() {
        return "member/joinUp";
    }
    @PostMapping("/joinUp")
    public String joinUp(MemberDto dto, HttpSession session){
        String confirmNumber = dto.getMailConfirmNumber();
        Integer expectedConfirmationCode = (Integer) session.getAttribute("confirmationCode");
        if (expectedConfirmationCode.toString().equals(confirmNumber)) {
            memberService.joinUp(dto);
        } else {
            session.setAttribute("emailVerificationMessage", "이메일 인증에 실패했습니다. 다시 시도해주세요.");
            return "redirect:/joinUp";
        }

        return "member/completed";
    }

}
