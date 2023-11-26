package com.bankingsystem.banking.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String name;
    private String password;
    private String password2;
    private String identification; //주민번호
    private String phoneNum;
    private String homeNum;
    private String address;
    private int authId; // 권한
}
