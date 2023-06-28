package com.field.muzi.web.admin.dto.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationUpdateRequest {

        private String type;
        private String reservationSeq;


}
