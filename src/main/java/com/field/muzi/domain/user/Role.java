package com.field.muzi.domain.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
//enum 사용이유? *enum 외의 값을 못 받도록 하기 위함(남/여 같이 범위 한정)
public enum Role {

    //스프링 시큐리티에서 권한 코드에 항상 'ROLE_' 이 앞에 있어야 한다. ex) ROLE_GUEST, ROLE_USER 등
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String value;

}
