package com.field.muzi.web.user.dto.diy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiyOptionsUpdateRequest {

    private String diyOptionsSeq;
    private Map<String, Object> content;
    private String etc;
}
