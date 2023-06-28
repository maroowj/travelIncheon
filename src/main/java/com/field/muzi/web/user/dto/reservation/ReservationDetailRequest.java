package com.field.muzi.web.user.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDetailRequest {

    private String reservationSeq;
    private String password;

}
