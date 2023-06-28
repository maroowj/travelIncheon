package com.field.muzi.web.client;

import com.field.muzi.FiledApplication;
import com.field.muzi.utils.enums.SocialType;
import com.field.muzi.web.client.dto.KakaoProfile;
import com.field.muzi.web.client.dto.OAuthTokenResponse;
import com.field.muzi.web.client.dto.SocialProfile;
import com.field.muzi.web.exception.social.CSocialException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class KaKaoOAuthClient implements OAuthClient {

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final WebClient webClient;
    private final FiledApplication application;

    @Value("${social.kakao.client-id}")
    private String kakaoClientId;

    @Value("${social.kakao.redirect}")
    private String kakaoRedirectUri;

    @Value("${social.kakao.url.login}")
    private String kakaoLoginUrl;

    @Value("${social.kakao.url.token}")
    private String kakaoTokenUrl;

    @Value("${social.kakao.url.profile}")
    private String kakaoProfileUrl;

    @Value("${social.kakao.url.unlink}")
    private String kakaoUnlinkUrl;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", kakaoClientId);
        params.put("redirect_uri", application.hostAddress() + kakaoRedirectUri);
        params.put("response_type", "code");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return kakaoLoginUrl + "?" + parameterString;
    }

    @Override
    public OAuthTokenResponse getTokenInfo(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", application.hostAddress() + kakaoRedirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(kakaoTokenUrl, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK)
            return gson.fromJson(response.getBody(), OAuthTokenResponse.class);
        throw new CSocialException.CSocialCommunicationException();
    }

    @Override
    public SocialProfile getProfile(String accessToken) {

        KakaoProfile kakaoProfile = webClient.post()
                .uri(kakaoProfileUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new CSocialException.CSocialCommunicationException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new CSocialException.CSocialCommunicationException()))
                .bodyToMono(KakaoProfile.class)
                .block();

        return new SocialProfile(kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getProperties().getNickname(), kakaoProfile.getKakao_account().getProfile().getProfile_image_url());
    }

    @Override
    public void unlink(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(kakaoUnlinkUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) return;
        throw new CSocialException.CSocialCommunicationException();
    }

}
