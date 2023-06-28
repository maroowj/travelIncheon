package com.field.muzi.web.user.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnacceptedMembersResponse {

    private String memberInfoSeq;
    private String thumbnailImageUrl;
    private String username;
    private String phone;
    private String createdAt;
}
