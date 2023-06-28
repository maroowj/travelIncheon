package com.field.muzi.web.user.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCourseListRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date searchDate;
    private String reservationDay;
    private String category;
    private String mainAddress;
    private String subAddress;
}
