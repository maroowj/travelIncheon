package com.field.muzi.web.common.dto.member;

import com.field.muzi.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
//회원가입 dto
public class UserSaveRequestDto{

    private String email;
    private String password;
    private Role authority;

}
