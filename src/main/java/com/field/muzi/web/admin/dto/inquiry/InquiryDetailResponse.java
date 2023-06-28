package com.field.muzi.web.admin.dto.inquiry;

import com.field.muzi.domain.entity.InquiryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDetailResponse {

    private String inquirySeq;
    private String name;
    private String snsId;
    private String businessName;
    private String businessType;
    private String serviceType;
    private String area;
    private String option;
    private String tel;
    private String email;
    private String address;
    private String content;
    private String answer;
    private String createDate;

    public InquiryDetailResponse(InquiryEntity inquiry) {
        this.setInquirySeq(inquiry.getInquirySeq());
        this.setName(inquiry.getName());
        this.setBusinessName(inquiry.getBusinessName());
        this.setBusinessType(inquiry.getBusinessType());
        this.setServiceType(inquiry.getServiceType());
        this.setArea(inquiry.getArea());
        this.setOption(inquiry.getOptionRequest());
        this.setTel(inquiry.getTel());
        this.setEmail(inquiry.getEmail());
        this.setAddress(inquiry.getAddress());
        this.setContent(inquiry.getContent());
        this.setAnswer(inquiry.getAnswer());
        this.setCreateDate(inquiry.getCreateDate().toString());
        this.setSnsId(inquiry.getMember().getMemberInfo().getEmail());
    }
}
