package com.field.muzi.web.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {


/*
	@GetMapping("/join")
	public String join() {
		return "/join";
	}

	@GetMapping("/login")
	public String login() {
		return "/login";
	}

	@GetMapping("/login/kakao/01")
	public String loginKakaoRedirect() {
		return "/snsRedirect/kakao01";
	}

	@GetMapping("/user/login/kakao/01")
	public String loginKakao(String code, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("code", code);
		redirectAttributes.addFlashAttribute("api_key", KAKAO_API_KEY);
		return "redirect:/login/kakao/01";
	}

	@GetMapping("/login/naver/01")
	public String loginNaverRedirect() {
		return "/snsRedirect/naver01";
	}

	@GetMapping("/user/login/naver/01")
	public String loginNaver(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("api_key", NAVER_API_KEY);
		return "redirect:/login/naver/01";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setPath("/");
			cookie.setValue(null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/";
	}
*/
}
