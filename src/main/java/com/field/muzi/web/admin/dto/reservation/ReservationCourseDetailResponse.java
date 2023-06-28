package com.field.muzi.web.admin.dto.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCourseDetailResponse {

        private List<FirstCourse> firstCourseList;
        private List<SecondCourse> secondCoursesList;


        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class FirstCourse {
                private String firstCourseTitle;
                private String courseType;
                private int runningTime;
                private String companyTitle;
                private String courseDetail;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class SecondCourse {
                private String secondCourseTitle;
                private int runningTime;
                private int cost;
                private String course;
                private List<String> reservationDay;
                private String costOption;
                private String address;
        }

}
