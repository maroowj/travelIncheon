package com.field.muzi.web.user.dto.member;

import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.domain.entity.MemberTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class MemberListResponseDto {

    private String userSeq;
    private String email;

    public static MemberListResponseDto of(MemberTypeEntity memberType) {
        MemberEntity member = memberType.getMember();
        return new MemberListResponseDto(member.getMemberSeq(), member.getMemberId());
    }
}
