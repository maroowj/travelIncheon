package com.field.muzi.web.admin.dto.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberListRequest {

    private String query;
}
