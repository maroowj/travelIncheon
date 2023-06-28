package com.field.muzi.utils;

import com.field.muzi.domain.entity.*;
import com.field.muzi.repository.*;

import com.field.muzi.web.exception.business.CEntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class EntityUtils {

    public static MemberEntity memberThrowable(MemberRepository memberRepository) {
        return memberRepository.findById(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(CEntityNotFoundException.CUserNotFoundException::new);
    }
    public static MemberEntity memberThrowable(MemberRepository memberRepository, String seq) {
        return memberRepository.findById(seq)
                .orElseThrow(CEntityNotFoundException.CUserNotFoundException::new);
    }

    public static MemberEntity memberThrowableByMemberId(MemberRepository memberRepository, String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(CEntityNotFoundException.CUserNotFoundException::new);
    }

    public static DiyEntity diyThrowable(DiyRepository diyRepository, String seq) {
        return diyRepository.findById(seq)
                .orElseThrow(CEntityNotFoundException.CDiyNotFoundException::new);
    }

    public static DiyDetailsEntity diyDetailsThrowable(DiyDetailsRepository diyDetailsRepository, String seq) {
        return diyDetailsRepository.findById(seq)
                .orElseThrow(CEntityNotFoundException.CDiyDetailsNotFoundException::new);
    }

    public static DiyOptionsEntity diyOptionsThrowable(DiyOptionsRepository diyOptionsRepository, String seq) {
        return diyOptionsRepository.findById(seq)
                .orElseThrow(CEntityNotFoundException.CDiyOptionsNotFoundException::new);
    }

    public static FormEntity formThrowable(FormRepository formRepository, String seq) {
        return formRepository.findById(seq)
                .orElseThrow(CEntityNotFoundException.CFormNotFoundException::new);
    }

    public static InquiryEntity inquiryThrowable(InquiryRepository inquiryRepository, String seq) {
        return inquiryRepository.findById(seq)
                .orElseThrow(CEntityNotFoundException.CInquiryNotFoundException::new);
    }

    public static SecondCourseEntity vrThrowable(SecondCourseRepository secondCourseRepository, String seq) {
        return secondCourseRepository.findById(seq)
                .orElseThrow(CEntityNotFoundException.CVrNotFoundException::new);
    }
}
