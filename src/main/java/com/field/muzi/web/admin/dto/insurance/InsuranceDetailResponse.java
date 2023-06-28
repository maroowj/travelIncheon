package com.field.muzi.web.admin.dto.insurance;

import com.field.muzi.domain.entity.FormEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceDetailResponse {

    private String seq;
    private String snsId;
    private Map<String, Object> content;
    private Map<String, Object> applicant;
    private String answer;
    private String fileName;
    private String fileUrl;
    private String formType;

    public InsuranceDetailResponse(FormEntity formEntity, String fileUrl) {
        this.setSeq(formEntity.getFormSeq());
        if(formEntity.getMember()!=null) {
            this.setSnsId(formEntity.getMember().getMemberInfo().getEmail());
        }else {
            this.setSnsId(null);
        }
        this.setContent(formEntity.getContent());
        this.setApplicant(formEntity.getApplicant());
        this.setAnswer(formEntity.getAnswer());
        this.setFormType(formEntity.getFormType());
        if(fileUrl!=null && !fileUrl.equals("")) {
            this.setFileName(formEntity.getExcel().getOriginName());
            this.setFileUrl(fileUrl);
        }
    }
}
