package com.bankingsystem.banking.member.dto;

import com.bankingsystem.banking.member.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class MemberDto {
    private String memberId;
    private String name;
    private String password;
    private String password2;
    private String mailConfirmNumber;
    private String identification; //주민번호
    private String phoneNum;
    private String homeNum;
    private String address;
    private int authId; // 권한

    public Member toEntity(){
        return Member.builder()
                .memberId(memberId)
                .name(name)
                .password(password)
                .identification(identification)
                .phoneNum(phoneNum)
                .homeNum(homeNum)
                .address(address)
                .authId(authId)
                .build();
    }
}
