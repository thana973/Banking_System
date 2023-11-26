package com.bankingsystem.banking.member.service.impl;

import com.bankingsystem.banking.member.AuthId;
import com.bankingsystem.banking.member.dto.MemberDto;
import com.bankingsystem.banking.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final PasswordEncoder passwordEncoder;
    @Override
    public int joinUp(MemberDto dto) {
        String pwd = dto.getPassword();
        // 비밀번호
        if(!isValidPassword(pwd)){
            return -1;
        }
        if(!StringUtils.equals(pwd,dto.getPassword2())){
            return -1;
        }
        // 주민번호
        if(!isValidIdentification(dto.getIdentification())){
            return -1;
        }
        // 핸드폰 번호
        if(!isValidPhoneNumber(dto.getPhoneNum())){
            return -1;
        }
        // 중복 가입확인
        if(isDuplicatedMember(dto.getIdentification())){
            return -1;
        }

        // 암호화
        dto.setPassword(passwordEncoder.encode(pwd));
        dto.setIdentification(passwordEncoder.encode(dto.getIdentification()));

        // 권한 추가
        dto.setAuthId(AuthId.USER.getValue());

        // 저장

        return 0;
    }
    private boolean isDuplicatedMember(String identification){
        return false;
    }
    private boolean isValidPassword(String password) {
        // 영문 대소문자, 숫자, 특수문자 포함하여 8~12자리
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$";
        return password.matches(passwordRegex);
    }
    private boolean isValidIdentification(String residentRegistrationNumber) {
        return residentRegistrationNumber.matches("^\\d{13}$");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{10,11}$");
    }
}
