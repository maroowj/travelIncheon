package com.field.muzi.web.user.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInsertRequest {

    private String firstCourseSeq;
    private List<String> secondCourseSeq;
    private String applicant;
    private String applicantDetail;
    private String guestType;
    private int guestNumber;
    private String managerPhone;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reservationDate;
    private int cost;
    private String vehicleType;
    private String vehicleNumber;
    private String etc;
    private String password;
    private Map<String,Object> terms1;
    private Map<String,Object> terms2;
    private Map<String,Object> terms3;
    private Map<String,Object> terms4;
    private Map<String,Object> terms5;
    private String email;
    private int busFare;
    private int guideFee;
    private int insuranceFee;
}
