package com.field.muzi.web.admin.dto.course;

import com.field.muzi.setup.FileManagerEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseFirstSaveRequest {

    private String companySeq;
    private String firstCourseTitle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String courseType;
    private String courseDetail;
    private int runningTime;
    private List<String> reservationTime;
    private List<String> reservationDay;
    private int applicant;
    private int applicantDetail;
    private int guestNumber;
    private int managerPhone;
    private int vehicle;
    private int etc;
    private int requirement;
    private String terms1;
    private String terms2;
    private String terms3;
    private String terms4;
    private String terms5;
    private MultipartFile thumbnailImage;
    private String descriptionImage;
    private String address;
}
