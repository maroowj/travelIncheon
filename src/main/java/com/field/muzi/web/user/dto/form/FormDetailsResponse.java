package com.field.muzi.web.user.dto.form;

import com.field.muzi.domain.entity.FormEntity;
import com.field.muzi.utils.LocalDateTimeProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormDetailsResponse {

    private String formSeq;
    private String title;
    private Map<String, Object> content;
    private String etc;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date confirmedDate;
    private String answer;
    private String fileName;
    private String fileUrl;

    public FormDetailsResponse(FormEntity form, String fileUrl){
        this.setFormSeq(form.getFormSeq());
        this.setTitle(form.getTitle());
        this.setContent(form.getContent());
        this.setEtc(form.getEtc());
        this.setCreateDate(form.getCreateDate());
        this.setConfirmedDate(form.getConfirmedDate());
        this.setAnswer(form.getAnswer());
        if(fileUrl!=null && !fileUrl.equals("")) {
            this.setFileName(form.getExcel().getOriginName());
            this.setFileUrl(fileUrl);
        }
    }
}
