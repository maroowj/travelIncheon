package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.SeqManager;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "reservation")
@DynamicInsert
@DynamicUpdate
@Entity
public class ReservationEntity {

    // strategy 수정 후 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_reservation")
    @GenericGenerator(name = "seq_manager_reservation", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
            @org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "rsvt_"),
            @org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
    })
    @Column(nullable = false, updatable = false, length = 15)
    @Id
    private String reservationSeq;

    @Column(length = 200)
    private String applicant;

    @Column(length = 200)
    private String applicantDetail;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(length = 200)
    private String guestType;

    @Column(columnDefinition = "int(10) default 0")
    private int guestNumber;

    @Column(length = 20)
    private String managerPhone;

    @Column(nullable = false)
    private Date reservationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firstCourseSeq")
    private FirstCourseEntity firstCourse;

    @Type(type = "json")
    @Column
    private List<String> secondCourse;

    @Column(length = 10)
    private int cost;

    @Column(length = 100)
    private String vehicleType;

    @Column(length = 20)
    private String vehicleNumber;

    @Column(length = 200)
    private String etc;

    @Type( type = "json")
    private HashMap<String, Object> terms1;

    @Type( type = "json")
    private HashMap<String, Object> terms2;

    @Type( type = "json")
    private HashMap<String, Object> terms3;

    @Type( type = "json")
    private HashMap<String, Object> terms4;

    @Type( type = "json")
    private HashMap<String, Object> terms5;

    @Column(nullable = false, length = 100)
    private String bookingStatus;

    @Column(length = 10)
    private int guideFee;

    @Column(length = 10)
    private int insuranceFee;

    @Column(length = 10)
    private int busFare;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "datetime default now()")
    private Date createDate;

    @Column
    @UpdateTimestamp
    private Date updateDate;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean withdrawal;


    @Builder
    public ReservationEntity(String applicant, String applicantDetail, String email, String password,
                             String guestType, int guestNumber, String managerPhone,
                             Date reservationDate, FirstCourseEntity firstCourse, List<String> secondCourse,
                             int cost, String vehicleType, String vehicleNumber, String etc, String bookingStatus,
                             HashMap<String, Object> terms1, HashMap<String, Object> terms2, HashMap<String, Object> terms3,
                             HashMap<String, Object> terms4, HashMap<String, Object> terms5,
                             int busFare, int guideFee, int insuranceFee) {

        this.applicant = applicant;
        this.applicantDetail = applicantDetail;
        this.email = email;
        this.password = password;
        this.guestType = guestType;
        this.guestNumber = guestNumber;
        this.managerPhone = managerPhone;
        this.reservationDate = reservationDate;
        this.firstCourse = firstCourse;
        this.secondCourse = secondCourse;
        this.cost = cost;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.etc = etc;
        this.bookingStatus = bookingStatus;
        this.terms1 = terms1;
        this.terms2 = terms2;
        this.terms3 = terms3;
        this.terms4 = terms4;
        this.terms5 = terms5;
        this.busFare = busFare;
        this.guideFee = guideFee;
        this.insuranceFee = insuranceFee;
    }

    public void cancelReservation() {
        this.withdrawal = true;
        this.bookingStatus = "예약취소";
    }

    public void updateDeposit() { this.bookingStatus = "입금완료";}

    public void updateConfirm() { this.bookingStatus = "예약확정";}

    public void updateCancel() { this.bookingStatus = "예약취소";}
}
