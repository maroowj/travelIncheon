package com.field.muzi.web.user.dto.inquiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquirySaveAndUpdateRequest {

    private String name;
    private String businessName;
    private String businessType;
    private String serviceType;
    private String area;
    private String option;
    private String tel;
    private String email;
    private String address;
    private String content;
}
