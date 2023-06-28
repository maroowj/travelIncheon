package com.field.muzi.web.common.controller;

import com.field.muzi.web.exception.business.CEntityNotFoundException;
import com.field.muzi.web.exception.security.CSecurityException;
import com.field.muzi.web.exception.security.CTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
@RequiredArgsConstructor
public class ExceptionController {

    @RequestMapping("/entrypoint")
    public void entryPointException() throws Exception {
        throw new CSecurityException.CAuthenticationEntryPointException();
    }
    @RequestMapping("/access-denied")
    public void accessDeniedException() {
        throw new CTokenException.CAccessDeniedException();
    }

    @RequestMapping(value = "/user-not-found")
    public void userNotFoundException() {
        throw new CEntityNotFoundException.CUserNotFoundException();
    }


}
