package com.field.muzi.web.user.service;

import com.field.muzi.domain.entity.FormEntity;
import com.field.muzi.domain.entity.InquiryEntity;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.repository.InquiryRepository;
import com.field.muzi.repository.MemberRepository;
import com.field.muzi.utils.EntityUtils;
import com.field.muzi.web.user.dto.form.FormDetailsResponse;
import com.field.muzi.web.user.dto.form.FormListResponse;
import com.field.muzi.web.user.dto.form.FormUpdateRequest;
import com.field.muzi.web.user.dto.inquiry.InquiryDetailsResponse;
import com.field.muzi.web.user.dto.inquiry.InquirySaveAndUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryService {

    private final MemberRepository memberRepository;
    private final InquiryRepository inquiryRepository;

    @Transactional
    public void create(InquirySaveAndUpdateRequest request) {
        MemberEntity member = EntityUtils.memberThrowable(memberRepository);
        inquiryRepository.save(
                InquiryEntity.create(
                        member,
                        request.getName(),
                        request.getBusinessName(),
                        request.getBusinessType(),
                        request.getServiceType(),
                        request.getArea(),
                        request.getOption(),
                        request.getTel(),
                        request.getEmail(),
                        request.getAddress(),
                        request.getContent()
                )
        );
    }

    @Transactional
    public void update(InquirySaveAndUpdateRequest request, String inquirySeq) {
        InquiryEntity inquiry = EntityUtils.inquiryThrowable(inquiryRepository, inquirySeq);
        inquiry.update(  request.getName(),
                request.getBusinessName(),
                request.getBusinessType(),
                request.getServiceType(),
                request.getArea(),
                request.getOption(),
                request.getTel(),
                request.getEmail(),
                request.getAddress(),
                request.getContent()
        );
    }

    @Transactional
    public Page<InquiryDetailsResponse> inquiryList(Pageable pageable) {
        MemberEntity member = EntityUtils.memberThrowable(memberRepository);
        return inquiryRepository.inquiryList(pageable, member);
    }

    @Transactional
    public InquiryDetailsResponse inquiryDetails(String inquirySeq) {
        InquiryEntity inquiry = EntityUtils.inquiryThrowable(inquiryRepository, inquirySeq);
        return new InquiryDetailsResponse(inquiry);
    }

    @Transactional
    public void deleteForm(String inquirySeq) {
        InquiryEntity inquiry = EntityUtils.inquiryThrowable(inquiryRepository, inquirySeq);
        inquiry.delete();
    }
}
