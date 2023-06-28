package com.field.muzi.web.admin.dto.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationListRequest {

        private String reservationSeq;
        private String companySeq;
        private String type;
        private String keyword;
        private String startDate;
        private String endDate;



}
