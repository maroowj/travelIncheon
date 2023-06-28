package com.field.muzi.web.common.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequest {

    private String memberId;
    private String memberPwd;
    private String provider;
    private boolean chkLogin;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberId, memberPwd);
    }

    public LoginRequest(String memberId, String memberPwd, String provider) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.provider = provider;
    }

    public LoginRequest(String id, String password) {
        this.memberId = id;
        this.memberPwd = password;
    }
}
