package com.field.muzi.repository;

import com.field.muzi.domain.entity.FormEntity;
import com.field.muzi.domain.entity.InquiryEntity;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.web.user.dto.inquiry.InquiryDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, String>, InquiryQueryRepository  {

}
