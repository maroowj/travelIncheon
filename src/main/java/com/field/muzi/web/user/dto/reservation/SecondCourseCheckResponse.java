package com.field.muzi.web.user.dto.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecondCourseCheckResponse {

    private String secondCourseSeq;
    private List<String> reservationDay;
}
