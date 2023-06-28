package com.field.muzi.web.user.controller.API;

import com.field.muzi.web.user.dto.course.*;
import com.field.muzi.web.user.service.CourseService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class CourseAPI {

    private final CourseService courseService;

    @GetMapping("/course/list")
    public AllCourseListResponse allCourseList(AllCourseListRequest request) {
        if(request.getSearchDate()==null && request.getReservationDay()==null) {
            return courseService.allCourseList(request);
        }else {
            return courseService.allCourseListForReservation(request);
        }
    }

    @GetMapping("/course/first/detail")
    public FirstCourseDetailResponse firstCourseDetail(String firstCourseSeq) {

        return courseService.firstCourseDetail(firstCourseSeq);
    }

    @GetMapping("/course/second/detail")
    public SecondCourseDetailResponse secondCourseDetail(String secondCourseSeq) {
        return courseService.secondCourseDetail(secondCourseSeq);
    }

    @GetMapping("/course/category")
    public List<CategoryGroup> categoryGroupList() {
        return courseService.categoryGroupList();
    }
}
