package com.field.muzi.web.common.dto.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefusedMemberMyInfoRequest {

    private String kakaoAccessToken;
}
