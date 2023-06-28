package com.field.muzi.web.user.controller.API;

import com.field.muzi.web.user.dto.reservation.ReservationCalendarRequest;
import com.field.muzi.web.user.dto.reservation.ReservationCheckRequest;
import com.field.muzi.web.user.dto.reservation.ReservationDetailRequest;
import com.field.muzi.web.user.dto.reservation.ReservationInsertRequest;
import com.field.muzi.web.user.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class ReservationAPI {

    private final ReservationService reservationService;

    @GetMapping("/reservation/check")
    public int reservationCheck(ReservationCheckRequest request) {
        return reservationService.reservationCheck(request);
    }

    @PostMapping("/reservation/insert")
    public int reservationInsert(ReservationInsertRequest request) throws Exception {
        return reservationService.reservationInsert(request);
    }

    @GetMapping("/reservation/detail")
    public Map<String, Object> userReservationDetail(ReservationDetailRequest request) {
        return reservationService.reservationDetail(request);
    }

    @GetMapping("/reservation/calendar")
    public List<ReservationService.Courses> reservationCalendar(ReservationCalendarRequest request) throws ParseException {
        return reservationService.reservationCalendar(request);
    }

    @PostMapping("/reservation/cancel")
    public int cancelReservation(ReservationDetailRequest request) {
        return reservationService.cancelReservation(request);
    }
}
