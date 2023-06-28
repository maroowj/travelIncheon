package com.field.muzi.web.admin.service;

import com.field.muzi.domain.entity.InquiryEntity;
import com.field.muzi.repository.InquiryRepository;
import com.field.muzi.utils.EntityUtils;
import com.field.muzi.web.admin.dto.inquiry.InquiryAnswerRequest;
import com.field.muzi.web.admin.dto.inquiry.InquiryDetailResponse;
import com.field.muzi.web.admin.dto.inquiry.InquiryListRequest;
import com.field.muzi.web.admin.dto.inquiry.InquiryListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminInquiryService {

    private final InquiryRepository inquiryRepository;

    @Transactional
    public Page<InquiryListResponse> inquiryListAdmin(Pageable pageable, InquiryListRequest request) {
        Page<InquiryListResponse> result = inquiryRepository.inquiryListAdmin(pageable, request);
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int totalCount = (int) result.getTotalElements();

        int dec = pageSize * pageNumber;

        for (InquiryListResponse response : result.getContent()) {
            response.setRowNum(totalCount - dec);
            dec++;
        }

        return result;
    }

    @Transactional
    public InquiryDetailResponse inquiryDetails(String inquirySeq) {
        InquiryEntity inquiry = EntityUtils.inquiryThrowable(inquiryRepository, inquirySeq);
        return new InquiryDetailResponse(inquiry);
    }

    @Transactional
    public void insertAnswer(String inquirySeq, InquiryAnswerRequest request) {
        InquiryEntity inquiry = EntityUtils.inquiryThrowable(inquiryRepository, inquirySeq);
        inquiry.answer(request.getAnswer());
    }
}
