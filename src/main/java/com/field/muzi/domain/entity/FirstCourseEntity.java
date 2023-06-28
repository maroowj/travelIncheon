package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.SeqManager;
import com.field.muzi.setup.FileManagerEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "firstCourse")
@DynamicInsert
@DynamicUpdate
@Entity
public class FirstCourseEntity {

    // strategy 수정 후 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_first_course")
    @GenericGenerator(name = "seq_manager_first_course", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
            @org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "frst_"),
            @org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
    })
    @Column(nullable = false, updatable = false, length = 15)
    @Id
    private String firstCourseSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companySeq")
    private CompanyEntity company;

    @Column(nullable = false, length = 200)
    private String firstCourseTitle;

    @Column(length = 200)
    private String courseType;

    @Column(length = 200)
    private String courseDetail;

    @Column(nullable = false, length = 10)
    private int runningTime;

    @Type(type = "json")
    @Column(nullable = false)
    private List<String> reservationTime;

    @Type(type = "json")
    @Column(nullable = false)
    private List<String> reservationDay;

    @Column(nullable = false)
    private Date startDate;

    @Column
    private Date endDate;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private boolean applicant;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private boolean applicantDetail;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private boolean guestNumber;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private boolean managerPhone;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private boolean vehicle;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private boolean etc;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private boolean requirement;

    @Column(columnDefinition = "TEXT")
    private String terms1;

    @Column(columnDefinition = "TEXT")
    private String terms2;

    @Column(columnDefinition = "TEXT")
    private String terms3;

    @Column(columnDefinition = "TEXT")
    private String terms4;

    @Column(columnDefinition = "TEXT")
    private String terms5;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "thumbnailImage")
    private FileManagerEntity thumbnailImage;

    @Column(columnDefinition = "TEXT")
    private String descriptionImage;

    @Column(length = 300)
    private String address;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "datetime default now()")
    private Date createDate;

    @Column
    @UpdateTimestamp
    private Date updateDate;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean withdrawal;

    @Builder
    public FirstCourseEntity(CompanyEntity company, String firstCourseTitle, String courseType, String courseDetail,
                             int runningTime, List<String> reservationTime, List<String> reservationDay, Date startDate, Date endDate,
                             boolean applicant, boolean applicantDetail, boolean guestNumber, boolean managerPhone, boolean vehicle,
                             boolean etc, boolean requirement, String terms1, String terms2, String terms3,
                             String terms4, String terms5, FileManagerEntity thumbnailImage, String descriptionImage, String address) {
        this.company = company;
        this.firstCourseTitle = firstCourseTitle;
        this.courseType = courseType;
        this.courseDetail = courseDetail;
        this.runningTime = runningTime;
        this.reservationTime = reservationTime;
        this.reservationDay = reservationDay;
        this.requirement = requirement;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applicant = applicant;
        this.applicantDetail = applicantDetail;
        this.guestNumber = guestNumber;
        this.managerPhone = managerPhone;
        this.vehicle = vehicle;
        this.etc = etc;
        this.terms1 = terms1;
        this.terms2 = terms2;
        this.terms3 = terms3;
        this.terms4 = terms4;
        this.terms5 = terms5;
        this.thumbnailImage = thumbnailImage;
        this.descriptionImage = descriptionImage;
        this.address = address;
    }

    public void deleteFirstCourse() {this.withdrawal = true;}

    public void setThumbnailImage(FileManagerEntity thumbnailImage) {this.thumbnailImage = thumbnailImage;}

    public void updateFirstCourseTitle(String firstCourseTitle) {this.firstCourseTitle = firstCourseTitle;}

    public void updateStartDate(Date startDate) {this.startDate = startDate;}

    public void updateEndDate(Date endDate) {this.endDate = endDate;}

    public void updateCourseType(String courseType) {this.courseType = courseType;}

    public void updateCourseDetail(String courseDetail) {this.courseDetail = courseDetail;}

    public void updateRunningTime(int runningTime) {this.runningTime = runningTime;}

    public void updateReservationTime(List<String> reservationTime) {this.reservationTime = reservationTime;}

    public void updateReservationDay(List<String> reservationDay) {this.reservationDay = reservationDay;}

    public void updateApplicant(boolean applicant) {this.applicant = applicant;}

    public void updateApplicantDetail(boolean applicantDetail) {this.applicantDetail = applicantDetail;}

    public void updateGuestNumber(boolean guestNumber) {this.guestNumber = guestNumber;}

    public void updateManagerPhone(boolean managerPhone) {this.managerPhone = managerPhone;}

    public void updateVehicle(boolean vehicle) {this.vehicle = vehicle;}

    public void updateEtc(boolean etc) {this.etc = etc;}

    public void updateRequirement(boolean requirement) {this.requirement = requirement;}

    public void updateTerms1(String terms1) {this.terms1 = terms1;}

    public void updateTerms2(String terms2) {this.terms2 = terms2;}

    public void updateTerms3(String terms3) {this.terms3 = terms3;}

    public void updateTerms4(String terms4) {this.terms4 = terms4;}

    public void updateTerms5(String terms5) {this.terms5 = terms5;}

    public void updateThumbNailImage(FileManagerEntity thumbnailImage) {this.thumbnailImage = thumbnailImage;}

    public void updateDescriptionImage(String descriptionImage) {this.descriptionImage = descriptionImage;}

    public void updateAddress(String address) {this.address = address;}

}
