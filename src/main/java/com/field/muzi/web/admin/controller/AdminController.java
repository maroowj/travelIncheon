package com.field.muzi.web.admin.controller;

import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.domain.user.Role;
import com.field.muzi.web.admin.service.CourseFirstService;
import com.field.muzi.web.admin.service.CourseSecondService;
import com.field.muzi.web.common.dto.TokenResponse;
import com.field.muzi.web.common.dto.member.LoginRequest;
import com.field.muzi.web.common.dto.member.SignupRequest;
import com.field.muzi.web.common.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final CourseFirstService firstService;
    private final AuthService authService;
    private final CourseSecondService secondService;
    private final PasswordEncoder passwordEncoder;

    // 관리자 메인페이지
    @GetMapping("")
    public String adminMain(HttpServletRequest request) {
        return "redirect:/admin/second-course";
    }

    // 관리자 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return "redirect:/admin/second-course";
        }
        return "/admin/login";
    }

    //관리자 등록
    @PostMapping("/signup")
    public MemberEntity signup(@RequestBody SignupRequest request) {
        return authService.signupAdmin(request);
    }

    // 관리자 로그인 페이지
    @ResponseBody
    @PostMapping("/login")
//    public ResponseEntity<TokenDto> adminLogin(@RequestBody LoginRequest request, HttpServletRequest req, HttpServletResponse response) {
    public TokenResponse adminLogin(@RequestBody LoginRequest request, HttpServletRequest req, HttpServletResponse response) {
//        List<Role> role = new ArrayList<>();
//        role.add(Role.ADMIN);
//        CookieUtils.removeCookieExceptFor(req, response, "savedId");

        TokenResponse tokenResponse = authService.login(request);
        Cookie access_cookie = new Cookie("AccessToken", tokenResponse.getAccessToken());
//        access_cookie.setMaxAge(60 * 60 * 24 );
        access_cookie.setMaxAge(-1);
        access_cookie.setPath("/");
        access_cookie.setHttpOnly(true);
        response.addCookie(access_cookie);

        Cookie refresh_cookie = new Cookie("RefreshToken", tokenResponse.getRefreshToken());
        if (request.isChkLogin()) {
            refresh_cookie.setMaxAge(60 * 60 * 24 * 7);
        } else {
            refresh_cookie.setMaxAge(-1);
        }
        refresh_cookie.setPath("/");
        refresh_cookie.setHttpOnly(true);
        response.addCookie(refresh_cookie);

        if (request.isChkLogin()) {
            Cookie loginSaveCookie = new Cookie("savedId", request.getMemberId());
            loginSaveCookie.setMaxAge(60 * 60 * 24 * 14); // 2주
            loginSaveCookie.setPath("/");
            response.addCookie(loginSaveCookie);
        } else {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("savedId")) {
                        cookie.setValue(null);
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        break;
                    }
                }
            }
        }
        return tokenResponse;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
        }

        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/admin/login";
    }

    // 업체관리 리스트 및 추가(모달)
    @GetMapping("/company")
    public String company() {
        return "/admin/company/company";
    }

    // 1차코스 리스트 및 상세(모달) 페이지
    @GetMapping("/first-course")
    public String firstCourse() {
        return "/admin/course1/list";
    }

    // 1차코스 추가
    @GetMapping("/first-course/insert")
    public String firstCourseInsert() {
        return "/admin/course1/insert";
    }

    // 1차코스 수정
    @GetMapping("/first-course/update")
    public String firstCourseUpdate(Model model, String firstCourseSeq) {
        model.addAttribute("course", firstService.courseFirstDetail(firstCourseSeq));
        return "/admin/course1/update";
    }

    // 2차코스 리스트 및 상세(모달) 페이지
    @GetMapping("/second-course")
    public String secondCourse() {
        return "/admin/course2/list";
    }

    // 2차코스 추가
    @GetMapping("/second-course/insert")
    public String secondCourseInsert() {
        return "/admin/course2/insert";
    }

    // 2차코스 수정
    @GetMapping("/second-course/update")
    public String secondCourseUpdate(Model model, String secondCourseSeq) {
        model.addAttribute("course", secondService.courseSecondDetail(secondCourseSeq));
        return "/admin/course2/update";
    }

    //예약관리
    @GetMapping("/reservation")
    public String reservation(){
        return "/admin/reservation/list";
    }

    //배너관리
    @GetMapping("/banner")
    public String banner(){
        return "/admin/banner/list";
    }

    //배너추가
    @GetMapping("/banner/insert")
    public String bannerAdd(){
        return "/admin/banner/insert";
    }

    //배너수정
    @GetMapping("/banner/update")
    public String bannerUpdate(){
        return "/admin/banner/update";
    }

    //부대비용
    @GetMapping("/unit/cost")
    public String unit(){
        return "/admin/unit/cost";
    }

    //VR 제작 의뢰
    @GetMapping("/qna/VR")
    public String vr(){
        return "/admin/member/VR";
    }

    //여행자 보험 관리
    @GetMapping("/qna/insurance")
    public String insurance(){
        return "/admin/member/insurance";
    }

    //회원 관리
    @GetMapping("/member")
    public String member(){
        return "/admin/member/list";
    }

}
