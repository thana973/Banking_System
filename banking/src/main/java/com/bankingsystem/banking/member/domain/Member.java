package com.bankingsystem.banking.member.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
@Entity
public class Member {
    @Id
    @Column(name = "MEMBER_ID",unique = true)
    private String memberId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "IDENTIFICATION", length = 500)
    private String identification; //주민번호
    @Column(name = "PHONE_NUM")
    private String phoneNum;
    @Column(name = "HOME_NUM")
    private String homeNum;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "AUTH_ID")
    private int authId; // 권한

    @Builder
    public Member(String memberId, String name, String password, String identification, String phoneNum, String homeNum, String address, int authId) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.identification = identification;
        this.phoneNum = phoneNum;
        this.homeNum = homeNum;
        this.address = address;
        this.authId = authId;
    }
}
