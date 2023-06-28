package com.field.muzi.repository;


import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.web.admin.dto.insurance.InsuranceListRequest;
import com.field.muzi.web.admin.dto.insurance.InsuranceListResponse;
import com.field.muzi.web.user.dto.form.FormListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FormQueryRepository {
    Page<FormListResponse> formListByMember(Pageable pageable, MemberEntity member);

    Page<InsuranceListResponse> insuranceList(Pageable pageable, InsuranceListRequest request);

}
