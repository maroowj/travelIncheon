package com.field.muzi.web.admin.controller.API;

import com.field.muzi.domain.entity.FirstCourseEntity;
import com.field.muzi.domain.entity.SecondCourseEntity;
import com.field.muzi.web.admin.dto.course.*;
import com.field.muzi.web.admin.service.CourseFirstService;
import com.field.muzi.web.admin.service.CourseSecondService;
import com.field.muzi.web.user.dto.course.SecondCourseDetailResponse;
import com.field.muzi.web.user.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class CourseSecondAPI {

    private final CourseSecondService courseSecondService;
    private final CourseService courseService;

    // 2차코스 목록 조회
    @GetMapping("/course/second/list")
    public Page<CourseSecondListResponse> courseSecondList(CourseSecondListRequest request, Pageable pageable) {
        return courseSecondService.courseSecondList(request, pageable);
    }

    // 2차코스 신규 등록
    @PostMapping("/course/second/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void courseSecondSave(CourseSecondSaveRequest request) throws Exception {
//        System.out.println(request);
        courseSecondService.courseSecondSave(request);
    }

    // 2차코스 상세 조회
    @GetMapping("/course/second/detail")
    public SecondCourseDetailResponse courseSecondDetail(String secondCourseSeq) {
        return courseService.secondCourseDetail(secondCourseSeq);
    }
//    public SecondCourseEntity courseSecondDetail(String secondCourseSeq) {
//        return courseSecondService.courseSecondDetail(secondCourseSeq);
//    }

    // 2차코스 수정
    @PostMapping("/course/second/update")
    public void courseSecondUpdate(CourseSecondUpdateRequest request) { courseSecondService.courseSecondUpdate(request); }

    //2차코스 삭제
    @PostMapping("/course/second/delete")
    public void courseSecondDelete(String secondCourseSeq) { courseSecondService.secondCourseDelete(secondCourseSeq); }

    // 2차코스 복제
    @PostMapping("/course/second/copy")
    public void secondCourseCopy(String secondCourseSeq) {
        courseSecondService.secondCourseCopy(secondCourseSeq);
    }
}
