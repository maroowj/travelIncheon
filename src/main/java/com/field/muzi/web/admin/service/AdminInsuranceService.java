package com.field.muzi.web.admin.service;

import com.field.muzi.domain.entity.FormEntity;
import com.field.muzi.repository.FormRepository;
import com.field.muzi.utils.EntityUtils;
import com.field.muzi.utils.handler.FileHandler;
import com.field.muzi.web.admin.dto.banner.BannerListResponse;
import com.field.muzi.web.admin.dto.inquiry.InquiryAnswerRequest;
import com.field.muzi.web.admin.dto.insurance.InsuranceDetailResponse;
import com.field.muzi.web.admin.dto.insurance.InsuranceListRequest;
import com.field.muzi.web.admin.dto.insurance.InsuranceListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.swing.text.html.parser.Entity;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminInsuranceService {

    private final FormRepository formRepository;
    private final FileHandler fileHandler;

    @Transactional
    public Page<InsuranceListResponse> insuranceList(Pageable pageable, InsuranceListRequest request) {
        Page<InsuranceListResponse> result = formRepository.insuranceList(pageable, request);

        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int totalCount = (int)result.getTotalElements();

        int dec = pageSize * pageNumber;

        for (InsuranceListResponse response : result.getContent()) {
            response.setRowNum(totalCount - dec);
            dec++;
        }

        return result;
    }

    @Transactional
    public InsuranceDetailResponse insuranceDetail(String seq) {
        FormEntity formEntity = EntityUtils.formThrowable(formRepository, seq);
        String fileUrl=null;
        if(!ObjectUtils.isEmpty(formEntity.getExcel())) {
            fileUrl = fileHandler.fileUrl(formEntity.getExcel());
        }
        return new InsuranceDetailResponse(formEntity, fileUrl);
    }

    @Transactional
    public void answer(String seq, InquiryAnswerRequest request) {
        FormEntity formEntity = EntityUtils.formThrowable(formRepository, seq);
        formEntity.answer(request.getAnswer());
    }
}
