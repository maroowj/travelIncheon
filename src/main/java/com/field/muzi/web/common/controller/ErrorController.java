package com.field.muzi.web.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
@RequestMapping("/error")
@RequiredArgsConstructor // 의존성 주입 어노테이션
public class ErrorController {

	private final static Logger log = Logger.getGlobal();

	@GetMapping("/denied")
	public String testInsert() {
		return "/error/denied";
	}

	@GetMapping("/admin-login")
	public String adminLoginError(HttpServletRequest request) {
		request.setAttribute("msg", "로그인 후 이용 가능합니다.");
		request.setAttribute("nextPage", "/admin/login");
		return "/error/ad_login";
	}

	@GetMapping("/user-drop")
	public String userDropError() {
		return "/error/user_drop";
	}
}