package com.field.muzi.web.admin.dto.insurance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceListResponse {

    private int rowNum;
    private String seq;
    private Map<String, Object> content;
    private String answer;
}
