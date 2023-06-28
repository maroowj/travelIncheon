package com.field.muzi.web.user.dto.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FirstCourseCheckResponse {

    private String firstCourseSeq;
    private List<String> reservationDay;
}
