package com.field.muzi.web.admin.dto.member;

import com.field.muzi.domain.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailResponse {

    private String snsId;
    private String name;
    private String createDate;
    private String gender;

    public MemberDetailResponse(MemberEntity member) {
        this.setSnsId(member.getMemberInfo().getEmail());
        this.setName(member.getMemberInfo().getMemberName());
        this.setCreateDate(member.getCreateDate().toString());
        if (member.getMemberInfo().getGender() != null && !member.getMemberInfo().getGender().equals("")) {
            if (member.getMemberInfo().getGender().equals("male")) {
                this.setGender("남");
            } else {
                this.setGender("여");
            }
        } else {
            this.setGender("-");
        }
    }
}
