package com.field.muzi.web.user.dto.course;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FirstCourseDetailResponse {

    private String firstCourseSeq;
    private String firstCourseTitle;
    private String companyTitle;
    private String companySeq;
    private String courseType;
    private String courseDetail;
    private int runningTime;
    private List<String> reservationTime;
    private List<String> reservationDay;
    private String startDate;
    private String endDate;
    private boolean applicant;
    private boolean applicantDetail;
    private boolean guestNumber;
    private boolean managerPhone;
    private boolean vehicle;
    private boolean etc;
    private boolean requirement;
    private String terms1;
    private String terms2;
    private String terms3;
    private String terms4;
    private String terms5;
    private Img thumbnailImage;
    private String descriptionImage;
    private String address;

    @Data
    @AllArgsConstructor
    public static class Img {
        private String fileSeq;
        private String fileName;
        private String url;
    }
}
