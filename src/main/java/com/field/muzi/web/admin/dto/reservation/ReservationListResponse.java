package com.field.muzi.web.admin.dto.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationListResponse {

        private String reservationSeq;
        private String bookingStatus;
        private String reservationDate;
        private String firstCourse;
        private String courseType;
        private List<Object> secondCourse;
        private String managerPhone;
        private String applicant;
        private String guestType;
        private Integer guestNumber;

}
