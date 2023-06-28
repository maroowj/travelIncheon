package com.field.muzi.web.admin.dto.course;

import com.field.muzi.web.user.dto.course.AllCourseListResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseSecondListResponse {

        private String secondCourseSeq;
        private String secondCourseTitle;
        private int cost;
        private String costOption;
        private int runningTime;
        private String startDate;
        private String endDate;
        private boolean withdrawal;
        private String category;
        private String link;


}
