package com.field.muzi.web.common.dto.member;

import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.domain.entity.MemberTypeEntity;
import com.field.muzi.domain.user.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class SignupRequest {

    private String memberId;
    private String memberPwd;
    private String provider;

}
