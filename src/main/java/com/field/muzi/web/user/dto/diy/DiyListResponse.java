package com.field.muzi.web.user.dto.diy;

import com.field.muzi.domain.entity.DiyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiyListResponse {

    private String DiySeq;
    private String DiyTitle;
    private String startDate;
    private String endDate;
    private String content;
    private String etc;

}
