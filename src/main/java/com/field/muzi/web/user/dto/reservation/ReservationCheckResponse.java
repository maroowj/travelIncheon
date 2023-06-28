package com.field.muzi.web.user.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCheckResponse {

    private String reservationSeq;
    private String reservationDate;
}
