package com.field.muzi.repository;

import com.field.muzi.domain.entity.CompanyEntity;
import com.field.muzi.web.admin.dto.reservation.ReservationListRequest;
import com.field.muzi.web.admin.dto.reservation.ReservationListResponse;
import com.field.muzi.web.common.dto.CommonCondition;
import com.field.muzi.web.user.dto.reservation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ReservationQueryRepository {

    List<ReservationCheckResponse> reservationCheckList(ReservationCheckRequest request);

    ReservationDetailResponse.Reservation userReservationInfo(ReservationDetailRequest request);

    List<MonthlyReservationCheck> checkList(String firstCourseSeq, Date reservationDate);

    Page<ReservationListResponse> reservationList(CompanyEntity company, ReservationListRequest request, Pageable pageable, CommonCondition condition);

    int reservationCountByFirstCourse(String firstCourseSeq);
    int reservationCountBySecondCourse(String secondCourseSeq);
}
