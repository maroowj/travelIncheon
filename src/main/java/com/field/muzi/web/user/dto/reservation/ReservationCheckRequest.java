package com.field.muzi.web.user.dto.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCheckRequest {

    private String firstCourseSeq;
    private List<String> secondCourseSeq;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reservationDate;
    private String reservationDay; // 예약 가능한 요일 체크


}
