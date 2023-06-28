package com.field.muzi.web.user.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondCourseDetailResponse {

    private String secondCourseSeq;
    private String secondCourseTitle;
    private String course;
    private List<String> reservationDay;
    private int runningTime;
    private String startDate;
    private String endDate;
    private int cost;
    private String videoUrl;
    private Img thumbnailImage;
    private String descriptionImage;
    private String address;
    private String category;
    private String link;

    @Data
    @AllArgsConstructor
    public static class Img {
        private String fileSeq;
        private String fileName;
        private String url;
    }
}
