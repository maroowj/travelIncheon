package com.field.muzi.web.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonCondition {
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFrom;
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTo;
}
