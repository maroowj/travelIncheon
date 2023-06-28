package com.field.muzi.web.admin.controller.API;

import com.field.muzi.web.admin.dto.inquiry.InquiryAnswerRequest;
import com.field.muzi.web.admin.dto.insurance.InsuranceDetailResponse;
import com.field.muzi.web.admin.dto.insurance.InsuranceListRequest;
import com.field.muzi.web.admin.dto.insurance.InsuranceListResponse;
import com.field.muzi.web.admin.service.AdminInsuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin")
public class AdminInsuranceAPI {

    private final AdminInsuranceService adminInsuranceService;

    @GetMapping("/insurance")
    public Page<InsuranceListResponse> insuranceList(@PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable, InsuranceListRequest request) {
        return adminInsuranceService.insuranceList(pageable, request);
    }

    @GetMapping("/insurance/{seq}")
    public InsuranceDetailResponse insuranceDetail(@PathVariable("seq") String seq) {
        return adminInsuranceService.insuranceDetail(seq);
    }

    @PutMapping("/insurance/{seq}")
    public void answer(@PathVariable("seq") String seq, @RequestBody InquiryAnswerRequest request) {
        adminInsuranceService.answer(seq, request);
    }
}
