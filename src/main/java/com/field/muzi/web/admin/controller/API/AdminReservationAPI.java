package com.field.muzi.web.admin.controller.API;

import com.field.muzi.web.admin.dto.reservation.ReservationListRequest;
import com.field.muzi.web.admin.dto.reservation.ReservationListResponse;
import com.field.muzi.web.admin.dto.reservation.ReservationUpdateRequest;
import com.field.muzi.web.admin.service.AdminReservationService;
import com.field.muzi.web.common.dto.CommonCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminReservationAPI {

    private final AdminReservationService adminReservationService;

    // 업체 목록 조회
    @GetMapping("/reservation/list")
    public Page<ReservationListResponse> reservationList(ReservationListRequest request, Pageable pageable, CommonCondition condition) {
        return adminReservationService.reservationList(request, pageable, condition);
    }

    // 업체 상세 조회
    @GetMapping("/reservation/detail")
    public Map<String, Object> reservationDetail(String reservationSeq) {
        return adminReservationService.reservationDetail(reservationSeq);
    }

    // 업체 수정
    @PostMapping("/reservation/update")
    public void reservationUpdate(ReservationUpdateRequest request) {
        adminReservationService.reservationUpdate(request);
    }

}
