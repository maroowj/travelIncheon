package com.field.muzi.web.user.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyFirstCourse {

    private String firstCourseSeq;
    private String firstCourseTitle;
    private List<String> reservationDay;
    private List<String> reservationTime;
}
