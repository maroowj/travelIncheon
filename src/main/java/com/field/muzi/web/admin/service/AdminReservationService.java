package com.field.muzi.web.admin.service;

import com.field.muzi.domain.entity.CompanyEntity;
import com.field.muzi.domain.entity.FirstCourseEntity;
import com.field.muzi.domain.entity.ReservationEntity;
import com.field.muzi.domain.entity.SecondCourseEntity;
import com.field.muzi.repository.CompanyRepository;
import com.field.muzi.repository.FirstCourseRepository;
import com.field.muzi.repository.ReservationRepository;
import com.field.muzi.repository.SecondCourseRepository;
import com.field.muzi.web.admin.dto.reservation.ReservationAdminDetailResponse;
import com.field.muzi.web.admin.dto.reservation.ReservationListRequest;
import com.field.muzi.web.admin.dto.reservation.ReservationListResponse;
import com.field.muzi.web.admin.dto.reservation.ReservationUpdateRequest;
import com.field.muzi.web.common.dto.CommonCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminReservationService {

    private final ReservationRepository reservationRepository;
    private final CompanyRepository companyRepository;
    private final SecondCourseRepository secondCourseRepository;
    private final FirstCourseRepository firstCourseRepository;

    // 예약 목록 조회
    @Transactional
    public Page<ReservationListResponse> reservationList(ReservationListRequest request, Pageable pageable, CommonCondition condition) {

        CompanyEntity company = companyRepository.findByCompanySeq(request.getCompanySeq());

        Page<ReservationListResponse> reservation = reservationRepository.reservationList(company, request, pageable, condition);

        for(ReservationListResponse response : reservation.getContent()) {
            if (response.getSecondCourse() != null) {
                for (int i = 0; i < response.getSecondCourse().size(); i++) {
                    String seq = new String((String) response.getSecondCourse().get(i));
                    response.getSecondCourse().set(i, secondCourseRepository.findById(seq).get().getSecondCourseTitle());
                }
            }
        }
       return reservation;
    }

    // 예약 상세 조회
    @Transactional
    public Map<String, Object> reservationDetail(String reservationSeq) {

        Map<String, Object> result = new HashMap<>();

        ReservationEntity reservation = reservationRepository.findById(reservationSeq).get();

//        FirstCourseEntity firstCourse = firstCourseRepository.

        List<String> secondCourseList = reservation.getSecondCourse();

        List<SecondCourseEntity> list = new ArrayList<>();

        if (secondCourseList != null) {
            for (String secondCourseSeq : secondCourseList) {
                SecondCourseEntity secondCourse = secondCourseRepository.findById(secondCourseSeq).get();
                list.add(secondCourse);
            }
        }

        try {
            result.put("reservationList", reservation);
            result.put("secondCourseList", list);

        } catch (NoSuchElementException noSuchElementException) {
            result.put("status", 1);
        }

        return result;
    }

    // 예약 수정
    @Transactional
    public void reservationUpdate(ReservationUpdateRequest request) {

        ReservationEntity reservation = reservationRepository.findById(request.getReservationSeq()).get();

        switch (request.getType()) {
            case "입금완료" :
                reservation.updateDeposit();
                break;

            case "예약확정" :
                reservation.updateConfirm();
                break;

            case "예약취소" :
                reservation.updateCancel();
                break;

            default:
                throw new RuntimeException("유효하지 않은 요청입니다.");

        }



    }

}
