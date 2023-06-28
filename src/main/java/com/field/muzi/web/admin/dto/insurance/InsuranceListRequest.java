package com.field.muzi.web.admin.dto.insurance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceListRequest {

    private String answer;
    private String query;
    private String travelType;
}
