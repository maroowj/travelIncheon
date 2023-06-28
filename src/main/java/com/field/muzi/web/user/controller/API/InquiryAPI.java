package com.field.muzi.web.user.controller.API;

import com.field.muzi.web.user.dto.form.FormDetailsResponse;
import com.field.muzi.web.user.dto.form.FormListResponse;
import com.field.muzi.web.user.dto.form.FormSaveRequest;
import com.field.muzi.web.user.dto.form.FormUpdateRequest;
import com.field.muzi.web.user.dto.inquiry.InquiryDetailsResponse;
import com.field.muzi.web.user.dto.inquiry.InquirySaveAndUpdateRequest;
import com.field.muzi.web.user.service.FormService;
import com.field.muzi.web.user.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class InquiryAPI {

    private final InquiryService inquiryService;

    @GetMapping("/inquiry")
    public Page<InquiryDetailsResponse> formList(@PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable) {
        return inquiryService.inquiryList(pageable);
    }

    @GetMapping("/inquiry/{inquirySeq}")
    public InquiryDetailsResponse inquiryDetails(@PathVariable("inquirySeq") String inquirySeq) {
        return inquiryService.inquiryDetails(inquirySeq);
    }

    @PostMapping("/inquiry")
    public void create(@RequestBody InquirySaveAndUpdateRequest request) {
        inquiryService.create(request);
    }

    @PutMapping("/inquiry/{inquirySeq}")
    public void update(@RequestBody InquirySaveAndUpdateRequest request, @PathVariable("inquirySeq") String inquirySeq) {
        inquiryService.update(request, inquirySeq);
    }

    @DeleteMapping("/inquiry/{inquirySeq}")
    public void deleteForm(@PathVariable("inquirySeq") String inquirySeq) {
        inquiryService.deleteForm(inquirySeq);
    }
}
