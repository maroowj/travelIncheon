package com.field.muzi.web.admin.dto.course;

import com.field.muzi.web.user.dto.course.AllCourseListResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseFirstLIstResponse {

        private String firstCourseSeq;
        private String firstCourseTitle;
        private String companyTitle;
        private String companySeq;
        private String companyColor;
        private List<String> reservationTime;
        private String startDate;
        private String endDate;
        private String courseType;
        private String courseDetail;
        private List<String> reservationDay;
        private String companyAddress;
        private boolean withdrawal;
        private AllCourseListResponse.Img thumbnailImage;
        private int runningTime;

}
