package com.field.muzi.web.user.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormUpdateRequest {
    private MultipartFile excel;

    private String title;
    private String content;
    private String etc;
    private String formType;
    private String applicant;

}
