package com.field.muzi.web.client;

import com.field.muzi.utils.enums.SocialType;
import com.field.muzi.web.client.dto.OAuthTokenResponse;
import com.field.muzi.web.client.dto.SocialProfile;

public interface OAuthClient {

    String getOauthRedirectURL();

    OAuthTokenResponse getTokenInfo(String code);

    SocialProfile getProfile(String accessToken);

    void unlink(String accessToken);

    default SocialType type() {
        if/* (this instanceof FacebookOAuthClient) {
            return SocialType.FACEBOOK;
        } else if (this instanceof GoogleOAuthClient) {
            return SocialType.GOOGLE;
        } else if (this instanceof NaverOAuthClient) {
            return SocialType.NAVER;
        } else if */(this instanceof KaKaoOAuthClient) {
            return SocialType.KAKAO;
        } else {
            return null;
        }
    }

}
