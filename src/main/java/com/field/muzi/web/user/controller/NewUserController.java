package com.field.muzi.web.user.controller;


import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.repository.MemberRepository;
import com.field.muzi.utils.EntityUtils;
import com.field.muzi.web.common.dto.KakaoSignupRequest;
import com.field.muzi.web.common.dto.TokenResponse;
import com.field.muzi.web.common.service.KakaoLoginService;
import com.field.muzi.web.user.dto.course.SecondCourseDetailResponse;
import com.field.muzi.web.user.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class NewUserController {

	private final KakaoLoginService kakaoLoginService;
	private final MemberRepository memberRepository;
	private final CourseService courseService;

	// 메인페이지
	@GetMapping()
	public String main() {
		return "/renew/index";
	}

	// 회사소개
	@GetMapping("/about")
	public String about() {
		return "/renew/about";
	}

	// VR투어 메인
	@GetMapping("/vr")
	public String vr_main() {
		return "/renew/vrList";
	}

	// VR투어 박물관
	@GetMapping("/vr/museum")
	public String vr_museum() {
		return "/renew/vrList01";
	}

	// VR투어 호텔
	@GetMapping("/vr/hotel")
	public String vr_hotel() {
		return "/renew/vrList02";
	}

	// VR투어 카페
	@GetMapping("/vr/cafe")
	public String vr_cafe() {
		return "/renew/vrList03";
	}

	// VR투어 기타
	@GetMapping("/vr/etc")
	public String vr_etc() {
		return "/renew/vrList04";
	}

	// VR투어 항공촬영
	@GetMapping("/vr/air")
	public String vr_air() {
		return "/renew/vrList05";
	}

	// VR투어 제작가격
	@GetMapping("/vr/price")
	public String vr_productionPrice() {
		return "/renew/vrPrice";
	}

	// VR투어 제작의뢰
	@GetMapping("/vr/inquiry")
	public String vr_productionRequest() {
		return "/renew/inquiry";
	}

	// 여행자보험
	@GetMapping("/insurance")
	public String insurance() {
		return "/renew/insurance";
	}

	// 마이페이지
	@GetMapping("/mypage")
	public String mypage() {
		return "/renew/mypage";
	}

	// 나의 문의 내역 상세
	@GetMapping("/mypage/inquiry/{inquirySeq}")
	public String inquiry_view(@PathVariable("inquirySeq") String inquirySeq) {
		return "/renew/inquiry_view";
	}

	// 나의 문의 내역 수정
	@GetMapping("/mypage/inquiry/update/{inquirySeq}")
	public String inquiry_update(@PathVariable("inquirySeq") String inquirySeq) {
		return "/renew/inquiry_update";
	}

	// 여행자 보험 내역 상세
	@GetMapping("/mypage/insurance/{insuranceSeq}")
	public String insurance_view(@PathVariable("insuranceSeq") String insuranceSeq) {
		return "/renew/insurance_view";
	}

	// 나의 문의 내역 수정
	@GetMapping("/mypage/insurance/update/{insuranceSeq}")
	public String insurance_update(@PathVariable("insuranceSeq") String insuranceSeq) {
		return "/renew/insurance_update";
	}

	/***********/

	// 1차 견학시설 상세페이지
	@GetMapping("/first-course")
	public String firstCourse() {
		return "/user/course/firstCourse";
	}

	// 2차 견학시설 상세페이지
	@GetMapping("/vr/{vrSeq}")
	public String secondCourse(@PathVariable("vrSeq") String vrSeq) {
		return "/renew/secondCourse";
	}

	// 예약 확인 페이지
	@GetMapping("/reservation")
	public String reservation() {
		return "/user/reservation/reservation";
	}

	/** 211020 우람 신규 추가 **/

	/** 사용자 카카오 로그인 **/
	@GetMapping("/login")
	public String login_view() {
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return "redirect:/";
		}

		return "/renew/login";
	}

	@GetMapping("/login/kakao")
	public void kakao_login(KakaoSignupRequest request, HttpServletResponse response) throws IOException {

		TokenResponse tokenResponse = kakaoLoginService.kakaoLogin(request);

		Optional<MemberEntity> optionalMember = memberRepository.findBySnsIdAndProvider(request.getSid(), request.getProvider());

		if(optionalMember.get().isWithdrawal()) {
			response.sendRedirect("/error/user-drop");
		}else {
			Cookie access_cookie = new Cookie("AccessToken", tokenResponse.getAccessToken());
//        access_cookie.setMaxAge(60 * 60 * 24 );
			access_cookie.setMaxAge(-1);
			access_cookie.setPath("/");
			access_cookie.setHttpOnly(true);
			response.addCookie(access_cookie);

			Cookie refresh_cookie = new Cookie("RefreshToken", tokenResponse.getRefreshToken());
			refresh_cookie.setMaxAge(-1);
			refresh_cookie.setPath("/");
			refresh_cookie.setHttpOnly(true);
			response.addCookie(refresh_cookie);

			response.sendRedirect("/");
		}
	}

	@GetMapping("/user/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setValue(null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

		response.sendRedirect("/");
	}

	@ResponseBody
	@GetMapping("/api/common/login")
	public Map<String, Object> loginChk() {
		Map<String, Object> result = new HashMap<>();
		String memberSeq = SecurityContextHolder.getContext().getAuthentication().getName();

		if (memberSeq == null || memberSeq.equals("") || memberSeq.equals("anonymousUser")) {
			result.put("login", false);
		}else {
			MemberEntity member = EntityUtils.memberThrowable(memberRepository);
			result.put("login", true);
			result.put("profilePicture", member.getMemberInfo().getProfileImage());
			result.put("name", member.getMemberInfo().getMemberName());
		}

		return  result;
	}

	@ResponseBody
	@GetMapping("/api/common/vr")
	public Page<SecondCourseDetailResponse> vrList(@PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable, String category) {
		return courseService.userVrList(pageable, category);
	}
}
