package com.field.muzi.web.user.dto.diy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiyDetailsUpdateRequest {

    private String diyDetailsSeq;
    private Map<String, Object> content;
    private String etc;
}
