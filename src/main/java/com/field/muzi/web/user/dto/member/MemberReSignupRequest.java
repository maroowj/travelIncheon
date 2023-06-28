package com.field.muzi.web.user.dto.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReSignupRequest {

    private String kakaoAccessToken;
    private String username;
    private String phone;
    private String email;
    private String recommenderCode;
    private Map<String, Object> infoJson;
}
