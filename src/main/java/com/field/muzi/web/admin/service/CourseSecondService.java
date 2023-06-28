package com.field.muzi.web.admin.service;

import com.field.muzi.domain.entity.SecondCourseEntity;
import com.field.muzi.repository.CompanyRepository;
import com.field.muzi.repository.SecondCourseRepository;
import com.field.muzi.repository.setup.FileManagerRepository;
import com.field.muzi.setup.FileManagerEntity;
import com.field.muzi.utils.handler.FileHandler;
import com.field.muzi.web.admin.dto.course.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseSecondService {

    private final CompanyRepository companyRepository;
    private final SecondCourseRepository secondCourseRepository;
    private final FileManagerRepository fileManagerRepository;

    private final FileHandler fileHandler;

    // 2차코스 목록 조회
    @Transactional
    public Page<CourseSecondListResponse> courseSecondList(CourseSecondListRequest request, Pageable pageable) {

       return secondCourseRepository.secondCourseList(request, pageable);
    }

    // 2차코스 신규 등록
    @Transactional
    public void courseSecondSave(CourseSecondSaveRequest request) throws Exception {

        List<MultipartFile> thumbnail = new ArrayList<>();

        SecondCourseEntity secondCourse = secondCourseRepository.save(
                SecondCourseEntity.builder()
                        .secondCourseTitle(request.getSecondCourseTitle())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .course(request.getCourse())
                        .address(request.getAddress())
                        .category(request.getCategory())
                        .runningTime(request.getRunningTime())
                        .reservationDay(request.getReservationDay())
                        .cost(request.getCost())
                        .costOption(request.getCostOption())
                        .descriptionImage(request.getDescriptionImage())
                        .link(request.getLink())
                        .build()
        );

        if (request.getThumbnailImage() != null && !request.getThumbnailImage().isEmpty()) {

            thumbnail.add(request.getThumbnailImage());
            FileManagerEntity img = fileManagerRepository.save(fileHandler.parse(thumbnail, "thum_img").get(0));
            secondCourse.setThumbnailImage(img);
        }
    }

    // 2차코스 상세 조회
    @Transactional
    public SecondCourseEntity courseSecondDetail(String secondCourseSeq) {
        return secondCourseRepository.findBySecondCourseSeq(secondCourseSeq);
    }

    // 2차코스 수정
    @Transactional
    public void courseSecondUpdate(CourseSecondUpdateRequest request) {
        SecondCourseEntity entity = secondCourseRepository.findBySecondCourseSeq(request.getSecondCourseSeq());

        if (request.getSecondCourseTitle() != null) {
            entity.updateSecondCourseTitle(request.getSecondCourseTitle());
        }
        if (request.getStartDate() != null) {
            entity.updateStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            entity.updateEndDate(request.getEndDate());
        }
        if (request.getCourse() != null) {
            entity.updateCourse(request.getCourse());
        }
        if (request.getAddress() != null) {
            entity.updateAddress(request.getAddress());
        }
        if (request.getCategory() != null) {
            entity.updateCategory(request.getCategory());
        }
        if (request.getRunningTime() != null) {
            entity.updateRunningTime(request.getRunningTime());
        }
        if (request.getReservationDay() != null) {
            entity.updateReservationDay(request.getReservationDay());
        }
        if (request.getCost() != null) {
            entity.updateCost(request.getCost());
        }
        if (request.getCostOption() != null) {
            entity.updateCostOption(request.getCostOption());
        }
        if (request.getDescriptionImage() != null) {
            entity.updateDescriptionImage(request.getDescriptionImage());
        }
        if (request.getLink() != null) {
            entity.updateLink(request.getLink());
        }

        if (request.getThumbnailImage() != null) {
            if (!request.getThumbnailImage().isEmpty()) {
                List<MultipartFile> imgs = new ArrayList<>();
                imgs.add(request.getThumbnailImage());
                try {
                    FileManagerEntity img = fileManagerRepository.save(fileHandler.parse(imgs, "thum_img").get(0));
                    entity.setThumbnailImage(img);
                } catch (Exception e) {
                    throw new RuntimeException("이미지 수정에 실패했습니다.");
                }
            }
        }
    }

    // 2차코스 삭제
    @Transactional
    public void secondCourseDelete(String secondCourseSeq) {
        SecondCourseEntity entity = secondCourseRepository.findBySecondCourseSeq(secondCourseSeq);
        entity.deleteSecondCourse();
    }

    // 2차코스 복제
    @Transactional
    public void secondCourseCopy(String secondCourseSeq) {
        SecondCourseEntity origin = secondCourseRepository.findBySecondCourseSeq(secondCourseSeq);

        SecondCourseEntity newCourse = secondCourseRepository.save(
                SecondCourseEntity.builder()
                        .secondCourseTitle(origin.getSecondCourseTitle())
                        .startDate(origin.getStartDate())
                        .endDate(origin.getEndDate())
                        .course(origin.getCourse())
                        .address(origin.getAddress())
                        .category(origin.getCategory())
                        .runningTime(origin.getRunningTime())
                        .reservationDay(origin.getReservationDay())
                        .cost(origin.getCost())
                        .costOption(origin.getCostOption())
                        .thumbnailImage(origin.getThumbnailImage())
                        .descriptionImage(origin.getDescriptionImage())
                        .build()
        );
    }
}
