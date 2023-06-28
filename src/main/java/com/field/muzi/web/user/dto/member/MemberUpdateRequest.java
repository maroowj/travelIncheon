package com.field.muzi.web.user.dto.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateRequest {

    private String username;
    private String phone;
    private String email;
    private String recommenderId;
    private Map<String, Object> infoJson;
}
