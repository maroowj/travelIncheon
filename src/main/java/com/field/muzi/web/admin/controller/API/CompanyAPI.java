package com.field.muzi.web.admin.controller.API;

import com.field.muzi.domain.entity.CompanyEntity;
import com.field.muzi.web.admin.dto.company.CompanySaveRequest;
import com.field.muzi.web.admin.dto.company.CompanyUpdateRequest;
import com.field.muzi.web.admin.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class CompanyAPI {

    private final CompanyService companyService;

    // 업체 목록 조회
    @GetMapping("/company/list")
    public List<CompanyEntity> companyList(String companyTitle) {
        return companyService.companyList(companyTitle);
    }

    // 업체 신규 등록
    @PostMapping("/company/save")
    public void companySave(@RequestBody CompanySaveRequest request) {
        companyService.companySave(request);
    }

    // 업체 상세 조회
    @GetMapping("/company/detail")
    public CompanyEntity companyDetail(String companySeq) {
        return companyService.companyDetail(companySeq);
    }

    // 업체 수정
    @PostMapping("/company/update")
    public void companyUpdate(@RequestBody CompanyUpdateRequest request) { companyService.companyUpdate(request); }

    //업체 삭제
    @PostMapping("/company/delete")
    public void companyDelete(String companySeq) { companyService.companyDelete(companySeq); }
}
