package com.field.muzi.utils;

import com.field.muzi.domain.entity.MemberEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtils {

    public static MemberEntity member() {
        return (MemberEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
