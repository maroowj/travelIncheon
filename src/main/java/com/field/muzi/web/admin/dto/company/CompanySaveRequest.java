package com.field.muzi.web.admin.dto.company;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanySaveRequest {

    private String companyTitle;
    private String companyAddress;
    private String companyColor;
    private String companyDescription;

}
