package com.field.muzi.web.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor
@ToString
public class TokenResponse {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}
