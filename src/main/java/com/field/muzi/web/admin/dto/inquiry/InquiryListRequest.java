package com.field.muzi.web.admin.dto.inquiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryListRequest {
    private String query;
    private String answer;
}
