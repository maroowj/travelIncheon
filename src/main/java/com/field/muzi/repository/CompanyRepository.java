package com.field.muzi.repository;

import com.field.muzi.domain.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, String>, CompanyQueryRepository {

    List<CompanyEntity> findAll();

    CompanyEntity findByCompanySeq(String companySeq);

    List<CompanyEntity> findByCompanyTitleContaining(String companyTitle);



}
