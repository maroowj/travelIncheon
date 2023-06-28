package com.field.muzi.web.admin.service;

import com.field.muzi.domain.entity.CompanyEntity;
import com.field.muzi.repository.CompanyRepository;
import com.field.muzi.web.admin.dto.company.CompanySaveRequest;
import com.field.muzi.web.admin.dto.company.CompanyUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    // 업체 목록 조회
    @Transactional
    public List<CompanyEntity> companyList(String companyTitle) {

        List<CompanyEntity> company = new ArrayList<>();

        if (companyTitle != null) {
            company = companyRepository.findByCompanyTitleContaining(companyTitle);
        } else {
            company = companyRepository.findAll();
        }

       return company;
    }

    // 업체 신규 등록
    @Transactional
    public void companySave(CompanySaveRequest request) {

        CompanyEntity company = companyRepository.save(
          CompanyEntity.builder()
                  .companyTitle(request.getCompanyTitle())
                  .companyAddress(request.getCompanyAddress())
                  .companyColor(request.getCompanyColor())
                  .companyDescription(request.getCompanyDescription())
                  .build()
        );
    }

    // 업체 상세 조회
    @Transactional
    public CompanyEntity companyDetail(String companySeq) {
        return companyRepository.findByCompanySeq(companySeq);
    }

    // 업체 수정
    @Transactional
    public void companyUpdate(CompanyUpdateRequest request) {
        CompanyEntity entity = companyRepository.findByCompanySeq(request.getCompanySeq());

        if (request.getCompanyTitle() != null) {
            entity.updateCompanyTitle(request.getCompanyTitle());
        }
        if (request.getCompanyColor() != null) {
            entity.updateCompanyColor(request.getCompanyColor());
        }
        if (request.getCompanyDescription() != null) {
            entity.updateCompanyDescription(request.getCompanyDescription());
        }

    }

    // 업체 삭제
    @Transactional
    public void companyDelete(String companySeq) {
        CompanyEntity entity = companyRepository.findByCompanySeq(companySeq);
        entity.deleteCompany();
    }


}
