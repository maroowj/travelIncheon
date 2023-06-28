package com.field.muzi.web.user.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDetailResponse {

    private Reservation reservation;
    private FirstCourse firstCourse;
    private List<SecondCourse> secondCourseList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reservation {
        private String reservationSeq;
        private String reservationDate;
        private Map<String, Object> terms1;
        private Map<String, Object> terms2;
        private Map<String, Object> terms3;
        private Map<String, Object> terms4;
        private Map<String, Object> terms5;
        private String applicant;
        private String applicantDetail;
        private String guestType;
        private int guestNumber;
        private String managerPhone;
        private String vehicleType;
        private String vehicleNumber;
        private String etc;
        private String firstCourseSeq;
        private List<String> secondCourseSeq;
        private String bookingStatus;
        private int busFare;
        private int guideFee;
        private int insuranceFee;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FirstCourse {
        private String firstCourseSeq;
        private String firstCourseTitle;
        private int runningTime;
        private String courseType;
        private String courseDetail;
        private boolean requirement;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SecondCourse {
        private String secondCourseSeq;
        private String secondCourseTitle;
        private int runningTime;
        private String course;
        private int cost;
        private String costOption;
    }

}
