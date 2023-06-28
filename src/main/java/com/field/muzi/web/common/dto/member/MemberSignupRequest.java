package com.field.muzi.web.common.dto.member;

import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.domain.entity.MemberTypeEntity;
import com.field.muzi.domain.user.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignupRequest {

    private String memberId;
    private String memberPwd;
    private Role role;

    // member info
    private String name;
    private String tel;
    private String email;

    public MemberTypeEntity toUser(PasswordEncoder passwordEncoder) {
        MemberEntity member = MemberEntity.builder()
                .memberId(memberId)
                .memberPwd(passwordEncoder.encode(memberPwd))
                .build();
        return MemberTypeEntity.builder()
                .member(member)
                .memberType(role)
                .build();
    }
}
