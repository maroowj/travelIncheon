package com.field.muzi.web.common.controller;

import com.field.muzi.domain.user.Role;
import com.field.muzi.web.common.dto.TokenRequest;
import com.field.muzi.web.common.dto.TokenResponse;
import com.field.muzi.web.common.dto.member.MemberSignupRequest;
import com.field.muzi.web.common.dto.member.TokenDto;
import com.field.muzi.web.common.dto.member.TokenRequestDto;
import com.field.muzi.web.common.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

/*    @PostMapping("/member/signup")
    public void memberSignup(@RequestBody MemberSignupRequest request) {
        authService.memberSignup(request);
    }*/
/*
    @PostMapping("/member/login")
    public ResponseEntity<TokenDto> memberLogin(@RequestBody MemberLoginRequest request,
                                HttpServletResponse response) {
        TokenDto tokenDto = authService.memberLogin(request);
        Cookie access_cookie = new Cookie("AccessToken", tokenDto.getAccessToken());
        access_cookie.setMaxAge(60 * 60 * 24);
        access_cookie.setPath("/");
        //access_cookie.setSecure(true);
        access_cookie.setHttpOnly(true);
        Cookie refresh_cookie = new Cookie("RefreshToken", tokenDto.getRefreshToken());
        refresh_cookie.setMaxAge(60 * 60 * 24 * 7);
        refresh_cookie.setPath("/");
        // refresh_cookie.setSecure(true);
        refresh_cookie.setHttpOnly(true);
        response.addCookie(access_cookie);
        response.addCookie(refresh_cookie);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/admin")
    public ResponseEntity<TokenDto> adminLogin(@RequestBody LoginRequest request,
                                          HttpServletResponse response) {
        TokenDto tokenDto = authService.login(request);
        Cookie access_cookie = new Cookie("AccessToken", tokenDto.getAccessToken());
        access_cookie.setMaxAge(60 * 60 * 24);
        access_cookie.setPath("/");
        //access_cookie.setSecure(true);
        access_cookie.setHttpOnly(true);
        Cookie refresh_cookie = new Cookie("RefreshToken", tokenDto.getRefreshToken());
        refresh_cookie.setMaxAge(60 * 60 * 24 * 7);
        refresh_cookie.setPath("/");
        // refresh_cookie.setSecure(true);
        refresh_cookie.setHttpOnly(true);
        response.addCookie(access_cookie);
        response.addCookie(refresh_cookie);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/super")
    public ResponseEntity<TokenDto> superLogin(@RequestBody LoginRequest request,
                                          HttpServletResponse response) {
        TokenDto tokenDto = authService.login(request);
        Cookie access_cookie = new Cookie("AccessToken", tokenDto.getAccessToken());
        access_cookie.setMaxAge(60 * 60 * 24);
        access_cookie.setPath("/");
        //access_cookie.setSecure(true);
        access_cookie.setHttpOnly(true);
        Cookie refresh_cookie = new Cookie("RefreshToken", tokenDto.getRefreshToken());
        refresh_cookie.setMaxAge(60 * 60 * 24 * 7);
        refresh_cookie.setPath("/");
        //refresh_cookie.setSecure(true);
        refresh_cookie.setHttpOnly(true);
        response.addCookie(access_cookie);
        response.addCookie(refresh_cookie);
        return ResponseEntity.ok(tokenDto);
    }
*/
    @PostMapping("/token/expiration") // 토큰 재발행? 재발급?
    public TokenResponse reissue(@RequestBody TokenRequest tokenRequest) {
        return authService.reissue(tokenRequest);
    }

//    @PostMapping("/group/{groupId}")
//    public boolean authenticateGroup(@PathVariable String groupId) {
//        return authService.authenticateGroup(groupId);
//    }

/*    @PostMapping("/check/role")
    public String roleCheck() {
        return authService.roleCheck();
    }

    @PostMapping("/super/check")
    public boolean superCheck(String id, String pw) {
        return authService.superCheck(id, pw);
    }*/
}
