package com.bankingsystem.banking.member.service.impl;

import com.bankingsystem.banking.member.AuthId;
import com.bankingsystem.banking.member.domain.Member;
import com.bankingsystem.banking.member.dto.MemberDto;
import com.bankingsystem.banking.member.repository.MemberRepository;
import com.bankingsystem.banking.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    @Override
    public boolean joinUp(MemberDto dto) {
        log.info("MemberServiceImpl.joinUp");
        String pwd = dto.getPassword();
        // 비밀번호
        if(!isValidPassword(pwd)){
            return false;
        }
        if(!StringUtils.equals(pwd,dto.getPassword2())){
            return false;
        }
        // 주민번호
        if(!isValidIdentification(dto.getIdentification())){
            return false;
        }
        // 핸드폰 번호
        if(!isValidPhoneNumber(dto.getPhoneNum())){
            return false;
        }
        // 중복 가입확인
        if(isDuplicatedMember(dto.getIdentification())){
            return false;
        }

        // 암호화
        dto.setPassword(passwordEncoder.encode(pwd));
        dto.setIdentification(passwordEncoder.encode(dto.getIdentification()));

        // 권한 추가
        dto.setAuthId(AuthId.USER.getValue());

        // 저장
        log.info(dto.toString());
        try {
            Member savedMember = memberRepository.save(dto.toEntity());
            // 저장 성공
            log.info("Member saved successfully. Member ID: " + savedMember.getMemberId());
            return true;
        } catch (DataAccessException e) {
            // 저장 실패
            log.info("Failed to save member. Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isIdDuplicate(String memberId) {
        return memberRepository.existsBymemberId(memberId);
    }

    private boolean isDuplicatedMember(String identification){
        boolean result = memberRepository.existsByIdentification(identification);
        log.info(String.valueOf(result));
        return result;
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
