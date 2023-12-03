package com.bankingsystem.banking.member.controller;

import com.bankingsystem.banking.member.service.MailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class MailController {
    private final MailService mailService;

    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail,HttpSession session){

        int number = mailService.sendMail(mail);
        session.setAttribute("confirmationCode", number);

        String num = "" + number;

        return num;
    }
}
