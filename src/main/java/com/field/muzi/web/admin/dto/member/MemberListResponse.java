package com.field.muzi.web.admin.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberListResponse {

    private int rowNum;
    private String memberSeq;
    private String snsId;
    private String name;
    private String createDate;
}
