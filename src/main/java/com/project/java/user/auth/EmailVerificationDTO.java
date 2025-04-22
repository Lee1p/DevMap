package com.project.java.user.auth;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자 이메일 인증 정보를 담는 DTO입니다.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationDTO {
    private String email;
    private String code;
    private Timestamp expireTime;
    private boolean verified;
    
    
    public EmailVerificationDTO(String email, String code, Timestamp expireTime) {
        this.email = email;
        this.code = code;
        this.expireTime = expireTime;
        this.verified = false;
    }
    
}
