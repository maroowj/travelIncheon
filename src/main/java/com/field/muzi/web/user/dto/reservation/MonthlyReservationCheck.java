package com.field.muzi.web.user.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReservationCheck {

    private String reservationSeq;
    private String applicant;
}
