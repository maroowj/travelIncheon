package com.field.muzi.web.admin.controller.API;

import com.field.muzi.web.admin.dto.inquiry.InquiryAnswerRequest;
import com.field.muzi.web.admin.dto.inquiry.InquiryDetailResponse;
import com.field.muzi.web.admin.dto.inquiry.InquiryListRequest;
import com.field.muzi.web.admin.dto.inquiry.InquiryListResponse;
import com.field.muzi.web.admin.service.AdminInquiryService;
import com.field.muzi.web.user.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminInquiryAPI {

    private final AdminInquiryService adminInquiryService;

    @GetMapping("/inquiry")
    public Page<InquiryListResponse> inquiryListResponses(@PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable, InquiryListRequest request) {
        return adminInquiryService.inquiryListAdmin(pageable, request);
    }

    @GetMapping("/inquiry/{inquirySeq}")
    public InquiryDetailResponse inquiryDetails(@PathVariable("inquirySeq") String inquirySeq) {
        return adminInquiryService.inquiryDetails(inquirySeq);
    }

    @PutMapping("/inquiry/{inquirySeq}")
    public void answer(@PathVariable("inquirySeq") String inquirySeq, @RequestBody InquiryAnswerRequest request) {
        adminInquiryService.insertAnswer(inquirySeq, request);
    }
}
