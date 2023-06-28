package com.field.muzi.repository;


import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.web.admin.dto.inquiry.InquiryListRequest;
import com.field.muzi.web.admin.dto.inquiry.InquiryListResponse;
import com.field.muzi.web.common.dto.CommonCondition;
import com.field.muzi.web.user.dto.form.FormListResponse;
import com.field.muzi.web.user.dto.inquiry.InquiryDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryQueryRepository {
    //사용자
    Page<InquiryDetailsResponse> inquiryList(Pageable pageable, MemberEntity member);

    //관리자
    Page<InquiryListResponse> inquiryListAdmin(Pageable pageable, InquiryListRequest request);
}
