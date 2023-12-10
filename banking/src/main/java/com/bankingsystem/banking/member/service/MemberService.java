package com.bankingsystem.banking.member.service;

import com.bankingsystem.banking.member.dto.MemberDto;

public interface MemberService {
    public boolean joinUp(MemberDto dto);

    public boolean isIdDuplicate(String memberId);
}
