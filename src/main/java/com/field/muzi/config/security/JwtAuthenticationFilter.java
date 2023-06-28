package com.field.muzi.config.security;


import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.repository.MemberRepository;
import com.field.muzi.utils.CookieUtils;
import com.field.muzi.web.common.dto.ErrorCode;
import com.field.muzi.web.exception.business.CEntityNotFoundException;
import com.field.muzi.web.exception.security.CSecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private static final String ACCESS_TOKEN = "AccessToken";
    private static final String REFRESH_TOKEN = "RefreshToken";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        boolean isSavedLogin = false;

        // 1. Request Header 에서 토큰을 꺼냄
        String token = null;

        if(CookieUtils.existsFor(request, ACCESS_TOKEN)) {
            if (CookieUtils.existsFor(request, REFRESH_TOKEN)) {
                token = CookieUtils.getValue(request, ACCESS_TOKEN);
            } else {
                Cookie cookie = CookieUtils.getCookie(request, ACCESS_TOKEN);
                cookie.setValue(null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        } else {
            token = jwtProvider.resolveToken(request);
        }

        //        log.info("[Verifying token]");
//        log.info(((HttpServletRequest) request).getRequestURL().toString());

        try {
//            if(StringUtils.hasText(token) && jwtProvider.validationToken(token)) {
//                Authentication authentication = jwtProvider.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else {
//                log.error("Security Error 1");
//                throw new CSecurityException.CAuthenticationEntryPointException();
//            }
            if(StringUtils.hasText(token)) {
                if (jwtProvider.validationToken(token)) {
                    Authentication authentication = jwtProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    if (CookieUtils.existsFor(request, "savedId")) {
//                        System.out.println("REQUEST URI: " + request.getRequestURI());
//                        System.out.println("TOKEN RESET <<<");
                        if (!request.getRequestURI().endsWith("logout")) {
                            Cookie kc = CookieUtils.getCookie(request, "savedId");
                            kc.setMaxAge(60 * 60 * 24 * 7);
                            kc.setPath("/");
                            kc.setHttpOnly(true);
                            response.addCookie(kc);
                            Cookie rc = CookieUtils.getCookie(request, REFRESH_TOKEN);
                            rc.setMaxAge(60 * 60 * 24 * 7);
                            rc.setPath("/");
                            rc.setHttpOnly(true);
                            response.addCookie(rc);
                        }
                    } else {
//                        if (!request.getRequestURI().endsWith("logout")) {
//                            Cookie rc = CookieUtils.getCookie(httpServletRequest, REFRESH_TOKEN);
//                            rc.setMaxAge(-1);
//                            rc.setPath("/");
//                            rc.setHttpOnly(true);
//                            response.addCookie(rc);
//                        }
                    }
                } else {
                    String rt = CookieUtils.getValue(request, REFRESH_TOKEN);
                    if (StringUtils.hasText(rt) && jwtProvider.validationToken(rt)) {
                        Cookie cookie = new Cookie("AccessToken", jwtProvider.generateAccessToken(rt));
                        cookie.setMaxAge(-1);
                        cookie.setPath("/");
                        cookie.setHttpOnly(true);
                        response.addCookie(cookie);
                    } else {
                        throw new CSecurityException.CAuthenticationEntryPointException();
                    }
                }
            } else {
                if (CookieUtils.existsFor(request, "savedId") && CookieUtils.existsFor(request, REFRESH_TOKEN)) {
                    String rt = CookieUtils.getValue(request, REFRESH_TOKEN);

                    if (StringUtils.hasText(rt) && jwtProvider.validationToken(rt)) {
                        Cookie cookie = new Cookie("AccessToken", jwtProvider.generateAccessToken(rt));
                        cookie.setMaxAge(-1);
                        cookie.setPath("/");
                        cookie.setHttpOnly(true);
                        response.addCookie(cookie);
                    } else {
                        throw new CSecurityException.CAuthenticationEntryPointException();
                    }
                } else {
//                log.error("Security Error 1");
                    throw new CSecurityException.CAuthenticationEntryPointException();
                }
            }
        } catch (CEntityNotFoundException.CUserNotFoundException e) {
//            log.error("Entity Not Found");
            request.setAttribute("exception", ErrorCode.USER_NOT_FOUND.getCode());
        } catch (CSecurityException.CAuthenticationEntryPointException e) {
//            log.error("Security Error 2");
            request.setAttribute("exception", ErrorCode.ACCESS_TOKEN_ERROR.getCode());
        } finally {
            chain.doFilter(request, response);
        }
    }


       /* // 쿠키 있을 때 자동로그인
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie cookiesSavedId = null;
            Cookie cookiesAccessToken = null;

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("AccessToken")) {
                    cookiesAccessToken = cookie;
                }
                if (cookie.getName().equals("savedId")) {
                    cookiesSavedId = cookie;
                }
            }

            if (cookiesSavedId != null && cookiesAccessToken == null) {
                MemberEntity member = memberRepository.findByMemberId(cookiesSavedId.getValue())
                        .orElseThrow(CEntityNotFoundException.CUserNotFoundException::new);
                // member토큰 만듬

                String accessToken = jwtProvider.savedLogin(member);

                //토큰으로 어센티케이션 만들고
                Authentication authentication = jwtProvider.getAuthentication(accessToken);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                cookiesAccessToken = new Cookie("AccessToken", accessToken);

                cookiesAccessToken.setMaxAge(60 * 60 * 24 * 14); // 2주

                cookiesAccessToken.setPath("/");

                response.addCookie(cookiesAccessToken);

                isSavedLogin = true;

            }

        }
        if (!isSavedLogin) {
            // 2. validateToken 으로 토큰 유형 검사
            // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
            try {
                if (StringUtils.hasText(token)) {
                    if (jwtProvider.validationToken(token)) {
                        Authentication authentication = jwtProvider.getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        Cookie rc = CookieUtils.getCookie(request, REFRESH_TOKEN);
                        rc.setMaxAge(60 * 60 * 24 * 7);
                        rc.setPath("/");
                        rc.setHttpOnly(true);
                        response.addCookie(rc);
                    } else {
                        String rt = CookieUtils.getValue(request, REFRESH_TOKEN);
                        if (StringUtils.hasText(rt) && jwtProvider.validationToken(rt)) {
                            Cookie cookie = new Cookie("AccessToken", jwtProvider.generateAccessToken(rt));
                            cookie.setMaxAge(-1);
                            cookie.setPath("/");
                            cookie.setHttpOnly(true);
                            response.addCookie(cookie);
                        } else {
                            log.error("Security Error 0");
                            throw new CSecurityException.CAuthenticationEntryPointException();
                        }
                    }
                }else {
                    log.error("Security Error 12112121");
                    if (CookieUtils.existsFor(request, "savedId") && CookieUtils.existsFor(request, REFRESH_TOKEN)) {
                        String rt = CookieUtils.getValue(request, REFRESH_TOKEN);

                        if (StringUtils.hasText(rt) && jwtProvider.validationToken(rt)) {
                            Cookie cookie = new Cookie("AccessToken", jwtProvider.generateAccessToken(rt));
                            cookie.setMaxAge(-1);
                            cookie.setPath("/");
                            cookie.setHttpOnly(true);
                            response.addCookie(cookie);
                        } else {
                            log.error("Security Error 1");
                            throw new CSecurityException.CAuthenticationEntryPointException();
                        }
                    } else {
                        log.error("Security Error 2");
                        throw new CSecurityException.CAuthenticationEntryPointException();
                    }
                }
            } catch (CEntityNotFoundException.CUserNotFoundException e) {
                request.setAttribute("exception", ErrorCode.USER_NOT_FOUND.getCode());
            } catch (CSecurityException.CAuthenticationEntryPointException e) {
                request.setAttribute("exception", ErrorCode.ACCESS_TOKEN_ERROR.getCode());
            } finally {
                chain.doFilter(request, response);
            }
        }*/


    }

/*    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String token = null;

        if(CookieUtils.existsFor(httpServletRequest, ACCESS_TOKEN)) {
            if (CookieUtils.existsFor(httpServletRequest, REFRESH_TOKEN)) {
                token = CookieUtils.getValue(httpServletRequest, ACCESS_TOKEN);
            } else {
                Cookie cookie = CookieUtils.getCookie(httpServletRequest, ACCESS_TOKEN);
                cookie.setValue(null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
//                return;
            }
        } else {
            token = jwtProvider.resolveToken(httpServletRequest);
        }

//        log.info("[Verifying token]");
//        log.info(((HttpServletRequest) request).getRequestURL().toString());

        try {
//            if(StringUtils.hasText(token) && jwtProvider.validationToken(token)) {
//                Authentication authentication = jwtProvider.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else {
//                log.error("Security Error 1");
//                throw new CSecurityException.CAuthenticationEntryPointException();
//            }
            if(StringUtils.hasText(token)) {
                if (jwtProvider.validationToken(token)) {
                    Authentication authentication = jwtProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    if (CookieUtils.existsFor(httpServletRequest, "LoginKeep")) {
//                        System.out.println("REQUEST URI: " + request.getRequestURI());
//                        System.out.println("TOKEN RESET <<<");
                        if (!request.getRequestURI().endsWith("logout")) {
                            Cookie kc = CookieUtils.getCookie(httpServletRequest, "LoginKeep");
                            kc.setMaxAge(60 * 60 * 24 * 7);
                            kc.setPath("/");
                            kc.setHttpOnly(true);
                            response.addCookie(kc);
                            Cookie rc = CookieUtils.getCookie(httpServletRequest, REFRESH_TOKEN);
                            rc.setMaxAge(60 * 60 * 24 * 7);
                            rc.setPath("/");
                            rc.setHttpOnly(true);
                            response.addCookie(rc);
                        }
                    } else {
//                        if (!request.getRequestURI().endsWith("logout")) {
//                            Cookie rc = CookieUtils.getCookie(httpServletRequest, REFRESH_TOKEN);
//                            rc.setMaxAge(-1);
//                            rc.setPath("/");
//                            rc.setHttpOnly(true);
//                            response.addCookie(rc);
//                        }
                    }
                } else {
                    String rt = CookieUtils.getValue(httpServletRequest, REFRESH_TOKEN);
                    if (StringUtils.hasText(rt) && jwtProvider.validationToken(rt)) {
                        Cookie cookie = new Cookie("AccessToken", jwtProvider.generateAccessToken(rt));
                        cookie.setMaxAge(-1);
                        cookie.setPath("/");
                        cookie.setHttpOnly(true);
                        response.addCookie(cookie);
                    } else {
                        throw new CSecurityException.CAuthenticationEntryPointException();
                    }
                }
            } else {
                if (CookieUtils.existsFor(httpServletRequest, "LoginKeep") && CookieUtils.existsFor(httpServletRequest, REFRESH_TOKEN)) {
                    String rt = CookieUtils.getValue(httpServletRequest, REFRESH_TOKEN);

                    if (StringUtils.hasText(rt) && jwtProvider.validationToken(rt)) {
                        Cookie cookie = new Cookie("AccessToken", jwtProvider.generateAccessToken(rt));
                        cookie.setMaxAge(-1);
                        cookie.setPath("/");
                        cookie.setHttpOnly(true);
                        response.addCookie(cookie);
                    } else {
                        throw new CSecurityException.CAuthenticationEntryPointException();
                    }
                } else {
                log.error("Security Error 1");
                    throw new CSecurityException.CAuthenticationEntryPointException();
                }
            }
        } catch (CEntityNotFoundException.CUserNotFoundException e) {
            log.error("Entity Not Found");
            request.setAttribute("exception", ErrorCode.USER_NOT_FOUND.getCode());
        } catch (CSecurityException.CAuthenticationEntryPointException e) {
            log.error("Security Error 2");
            request.setAttribute("exception", ErrorCode.ACCESS_TOKEN_ERROR.getCode());
        } finally {
            chain.doFilter(request, response);
        }
    }*/

/*
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String token = jwtProvider.resolveToken((HttpServletRequest) request);

        log.info("[Verifying token]");
        log.info(((HttpServletRequest) request).getRequestURL().toString());

        try {
            if(StringUtils.hasText(token) && jwtProvider.validationToken(token)) {
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new CSecurityException.CAuthenticationEntryPointException();
            }
        } catch (CEntityNotFoundException.CUserNotFoundException e) {
            request.setAttribute("exception", ErrorCode.USER_NOT_FOUND.getCode());
        } catch (CSecurityException.CAuthenticationEntryPointException e) {
            request.setAttribute("exception", ErrorCode.ACCESS_TOKEN_ERROR.getCode());
        } finally {
            chain.doFilter(request, response);
        }
    }
    */

