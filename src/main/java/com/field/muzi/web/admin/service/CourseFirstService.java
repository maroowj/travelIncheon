package com.field.muzi.web.admin.service;

import com.field.muzi.domain.entity.CompanyEntity;
import com.field.muzi.domain.entity.FirstCourseEntity;
import com.field.muzi.repository.CompanyRepository;
import com.field.muzi.repository.FirstCourseRepository;
import com.field.muzi.repository.ReservationRepository;
import com.field.muzi.repository.SecondCourseRepository;
import com.field.muzi.repository.setup.FileManagerRepository;
import com.field.muzi.setup.FileManagerEntity;
import com.field.muzi.utils.handler.FileHandler;
import com.field.muzi.web.admin.dto.course.CourseFirstLIstResponse;
import com.field.muzi.web.admin.dto.course.CourseFirstListRequest;
import com.field.muzi.web.admin.dto.course.CourseFirstSaveRequest;
import com.field.muzi.web.admin.dto.course.CourseFirstUpdateRequest;
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
public class CourseFirstService {

    private final CompanyRepository companyRepository;
    private final FirstCourseRepository firstCourseRepository;
    private final SecondCourseRepository secondCourseRepository;
    private final FileManagerRepository fileManagerRepository;
    private final ReservationRepository reservationRepository;

    private final FileHandler fileHandler;

    // 1차코스 목록 조회
    @Transactional
    public Page<CourseFirstLIstResponse> courseFirstList(CourseFirstListRequest request, String CompanySeq, Pageable pageable) {

        CompanyEntity company = companyRepository.findByCompanySeq(CompanySeq);

       return firstCourseRepository.firstCourseList(request, company, pageable);
    }

    // 1차코스 신규 등록
    @Transactional
    public void courseFirstSave(CourseFirstSaveRequest request) throws Exception {

        CompanyEntity company = companyRepository.findByCompanySeq(request.getCompanySeq());

        List<MultipartFile> thumbnail = new ArrayList<>();

        FirstCourseEntity firstCourse = firstCourseRepository.save(
                FirstCourseEntity.builder()
                        .company(company)
                        .firstCourseTitle(request.getFirstCourseTitle())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .courseType(request.getCourseType())
                        .courseDetail(request.getCourseDetail())
                        .runningTime(request.getRunningTime())
                        .reservationTime(request.getReservationTime())
                        .reservationDay(request.getReservationDay())
                        .applicant(request.getApplicant() == 1)
                        .applicantDetail(request.getApplicantDetail() == 1)
                        .managerPhone(request.getManagerPhone() == 1)
                        .vehicle(request.getVehicle() == 1)
                        .etc(request.getEtc() == 1)
                        .requirement(request.getRequirement() == 1)
                        .terms1(request.getTerms1())
                        .terms2(request.getTerms2())
                        .terms3(request.getTerms3())
                        .terms4(request.getTerms4())
                        .terms5(request.getTerms5())
                        .descriptionImage(request.getDescriptionImage())
                        .address(request.getAddress())
                        .build()
        );

        if (request.getThumbnailImage() != null && request.getThumbnailImage().isEmpty()) {

            thumbnail.add(request.getThumbnailImage());
            FileManagerEntity img = fileManagerRepository.save(fileHandler.parse(thumbnail, "thum_img").get(0));
            firstCourse.setThumbnailImage(img);
        }
    }

    // 1차코스 상세 조회
    @Transactional
    public FirstCourseEntity courseFirstDetail(String firstCourseSeq) {
        return firstCourseRepository.findByFirstCourseSeq(firstCourseSeq);
    }

    // 1차코스 수정
    @Transactional
    public void courseFirstUpdate(CourseFirstUpdateRequest request) {
        FirstCourseEntity entity = firstCourseRepository.findByFirstCourseSeq(request.getFirstCourseSeq());


            if (request.getFirstCourseTitle() != null) {
                entity.updateFirstCourseTitle(request.getFirstCourseTitle());
            }
            if (request.getStartDate() != null) {
                entity.updateStartDate(request.getStartDate());
            }
            if (request.getEndDate() != null) {
                entity.updateEndDate(request.getEndDate());
            }
            if (request.getCourseType() != null) {
                entity.updateCourseType(request.getCourseType());
            }
            if (request.getCourseDetail() != null) {
                entity.updateCourseDetail(request.getCourseDetail());
            }
            if (request.getRunningTime() != null) {
                entity.updateRunningTime(request.getRunningTime());
            }
            if (request.getReservationTime() != null) {
                entity.updateReservationTime(request.getReservationTime());
            }
            if (request.getReservationDay() != null) {
                entity.updateReservationDay(request.getReservationDay());
            }
            if (request.getApplicant() != null) {
                entity.updateApplicant(request.getApplicant() == 1);
            }
            if (request.getApplicantDetail() != null) {
                entity.updateApplicantDetail(request.getApplicantDetail() == 1);
            }
            if (request.getGuestNumber() != null) {
                entity.updateGuestNumber(request.getGuestNumber() == 1);
            }
            if (request.getManagerPhone() != null) {
                entity.updateManagerPhone(request.getManagerPhone() == 1);
            }
            if (request.getVehicle() != null) {
                entity.updateVehicle(request.getVehicle() == 1);
            }
            if (request.getEtc() != null) {
                entity.updateEtc(request.getEtc() == 1);
            }
            if (request.getRequirement() != null) {
                entity.updateRequirement(request.getRequirement() == 1);
            }
            if (request.getTerms1() != null) {
                entity.updateTerms1(request.getTerms1());
            }
            if (request.getTerms2() != null) {
                entity.updateTerms2(request.getTerms2());
            }
            if (request.getTerms3() != null) {
                entity.updateTerms3(request.getTerms3());
            }
            if (request.getTerms4() != null) {
                entity.updateTerms4(request.getTerms4());
            }
            if (request.getTerms5() != null) {
                entity.updateTerms5(request.getTerms5());
            }
            if (request.getDescriptionImage() != null) {
                entity.updateDescriptionImage(request.getDescriptionImage());
            }
            if (request.getThumbnailImage() != null && !request.getThumbnailImage().isEmpty()) {
                List<MultipartFile> imgs = new ArrayList<>();
                imgs.add(request.getThumbnailImage());
                try {
                    FileManagerEntity img = fileManagerRepository.save(fileHandler.parse(imgs, "thum_img").get(0));
                    entity.updateThumbNailImage(img);
                } catch (Exception e) {
                    throw new RuntimeException("이미지 수정에 실패했습니다.");
                }
            }
            if (request.getAddress() != null) {
                entity.updateAddress(request.getAddress());
            }

    }

    // 1차코스 삭제
    @Transactional
    public void firstCourseDelete(String firstCourseSeq) {
        FirstCourseEntity entity = firstCourseRepository.findByFirstCourseSeq(firstCourseSeq);
        entity.deleteFirstCourse();
    }

    // 1차코스 복사
    @Transactional
    public void firstCourseCopy(String firstCourseSeq) {
        FirstCourseEntity origin = firstCourseRepository.findByFirstCourseSeq(firstCourseSeq);

        boolean applicant;
        boolean applicantDetail;
        boolean guestNumber;
        boolean managerPhone;
        boolean vehicle;
        boolean etc;
        boolean requirement;

        FirstCourseEntity newCourse = firstCourseRepository.save(
                FirstCourseEntity.builder()
                        .company(origin.getCompany())
                        .firstCourseTitle(origin.getFirstCourseTitle())
                        .startDate(origin.getStartDate())
                        .endDate(origin.getEndDate())
                        .courseType(origin.getCourseType())
                        .courseDetail(origin.getCourseDetail())
                        .runningTime(origin.getRunningTime())
                        .reservationTime(origin.getReservationTime())
                        .reservationDay(origin.getReservationDay())
                        .applicant(origin.isApplicant())
                        .applicantDetail(origin.isApplicantDetail())
                        .managerPhone(origin.isManagerPhone())
                        .vehicle(origin.isVehicle())
                        .etc(origin.isEtc())
                        .requirement(origin.isRequirement())
                        .terms1(origin.getTerms1())
                        .terms2(origin.getTerms2())
                        .terms3(origin.getTerms3())
                        .terms4(origin.getTerms4())
                        .terms5(origin.getTerms5())
                        .descriptionImage(origin.getDescriptionImage())
                        .address(origin.getAddress())
                        .thumbnailImage(origin.getThumbnailImage())
                        .descriptionImage(origin.getDescriptionImage())
                        .build()
        );

    }

}
