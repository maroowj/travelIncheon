package com.field.muzi.web.user.dto.course;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCourseListResponse {

    private List<FirstCourse> firstCourseList;
    private List<SecondCourse> secondCoursesList;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FirstCourse {
        private String firstCourseSeq;
        private String firstCourseTitle;
        private String companyTitle;
        private String startDate;
        private String endDate;
        private String courseType;
        private String courseDetail;
        private List<String> reservationDay;
        private Img thumbnailImage;
        private int runningTime;
        private String address;
//        private String thumbnailImage;
//        private String descriptionImage;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SecondCourse {
        private String secondCourseSeq;
        private String secondCourseTitle;
        private int runningTime;
        private int cost;
        private String course;
        private List<String> reservationDay;
        private String costOption;
        private String address;
        private String category;
        private Img thumbnailImage;
//        private String thumbnailImage;
//        private String descriptionImage;
    }

    @Data
    @AllArgsConstructor
    public static class Img {
        private String fileSeq;
        private String fileName;
        private String url;
    }
}
