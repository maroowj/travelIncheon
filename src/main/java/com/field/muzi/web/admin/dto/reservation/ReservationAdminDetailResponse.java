package com.field.muzi.web.admin.dto.reservation;

import com.field.muzi.web.user.dto.course.AllCourseListResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationAdminDetailResponse {

        private String reservationDate;
        private String bookingStatus;
        private String applicant;
        private String applicantDetail;
        private String guestType;
        private String guestNumber;
        private String managerPhone;
        private String etc;
        //        private String firstCourse;
        //        private List<String> secondCourse;
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
