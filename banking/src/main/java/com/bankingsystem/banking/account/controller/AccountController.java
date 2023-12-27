package com.bankingsystem.banking.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/")
    @ResponseBody
    public String controllerTest(){
        return "test";
    }

}
