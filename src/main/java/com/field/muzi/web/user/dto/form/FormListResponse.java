package com.field.muzi.web.user.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormListResponse {

    private String formSeq;
    private String title;
    private String category;
    private String createDate;
    private String answer;
}
