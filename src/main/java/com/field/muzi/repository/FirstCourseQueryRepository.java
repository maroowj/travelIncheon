package com.field.muzi.repository;

import com.field.muzi.domain.entity.CompanyEntity;
import com.field.muzi.web.admin.dto.course.CourseFirstLIstResponse;
import com.field.muzi.web.admin.dto.course.CourseFirstListRequest;
import com.field.muzi.web.user.dto.course.AllCourseListRequest;
import com.field.muzi.web.user.dto.course.AllCourseListResponse;
import com.field.muzi.web.user.dto.course.FirstCourseDetailResponse;
import com.field.muzi.web.user.dto.reservation.MonthlyFirstCourse;
import com.field.muzi.web.user.dto.reservation.ReservationDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface FirstCourseQueryRepository {

    List<AllCourseListResponse.FirstCourse> userFirstCourseList(AllCourseListRequest request);
    FirstCourseDetailResponse firstCourseDetail(String firstCourseSeq);

    ReservationDetailResponse.FirstCourse reservationFirstCourse(String firstCourseSeq);

    Page<CourseFirstLIstResponse> firstCourseList(CourseFirstListRequest request, CompanyEntity company, Pageable pageable);

    List<MonthlyFirstCourse> monthlyFirstCourseList(Date searchDate);
}
