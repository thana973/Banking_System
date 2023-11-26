package com.bankingsystem.banking.member.domain;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    private String name;
    private String password;
    private String password2;
    private String identification; //주민번호
    private String phoneNum;
    private String homeNum;
    private String address;
    private int authId; // 권한
}
