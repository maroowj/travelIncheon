package com.field.muzi.repository;

import com.field.muzi.web.admin.dto.course.CourseSecondListRequest;
import com.field.muzi.web.admin.dto.course.CourseSecondListResponse;
import com.field.muzi.web.user.dto.course.AllCourseListRequest;
import com.field.muzi.web.user.dto.course.AllCourseListResponse;
import com.field.muzi.web.user.dto.course.CategoryGroup;
import com.field.muzi.web.user.dto.course.SecondCourseDetailResponse;
import com.field.muzi.web.user.dto.reservation.ReservationDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SecondCourseQueryRepository {

    List<AllCourseListResponse.SecondCourse> userSecondCourseList(AllCourseListRequest request);
    SecondCourseDetailResponse secondCourseDetail(String secondCourseSeq);

    ReservationDetailResponse.SecondCourse reservationSecondCourse(String secondCourseSeq);

    Page<CourseSecondListResponse> secondCourseList(CourseSecondListRequest request, Pageable pageable);

    Page<SecondCourseDetailResponse> secondCourseList(Pageable pageable, String category);

    List<CategoryGroup> categoryGroup();
}
