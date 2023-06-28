package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.SeqManager;
import com.field.muzi.setup.FileManagerEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "secondCourse")
@DynamicInsert
@DynamicUpdate
@Entity
public class SecondCourseEntity {
    // strategy 수정 후 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_second_course")
    @GenericGenerator(name = "seq_manager_second_course", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
            @org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "scnd_"),
            @org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
    })
    @Column(nullable = false, updatable = false, length = 15)
    @Id
    private String secondCourseSeq;

    @Column(nullable = false, length = 200)
    private String secondCourseTitle;

    @Column(length = 200)
    private String course;

    @Column(length = 300)
    private String address;

    @Column(length = 200)
    private String category;

    @Type(type = "json")
    @Column(nullable = false)
    private List<String> reservationDay;

    @Column(nullable = false, length = 10)
    private int runningTime;

    @Column(nullable = false)
    private Date startDate;

    @Column
    private Date endDate;

    @Column(nullable = false, columnDefinition = "int(10) default 0")
    private int cost;

    @Column(length = 300)
    private String costOption;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "thumbnailImage")
    private FileManagerEntity thumbnailImage;

    @Column(columnDefinition = "TEXT")
    private String descriptionImage;

    @Column(length = 200)
    private String mainAddress;

    @Column(length = 200)
    private String subAddress;

    @Column(length = 200)
    private String link;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "datetime default now()")
    private Date createDate;

    @Column
    @UpdateTimestamp
    private Date updateDate;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean withdrawal;

    @Builder
    public SecondCourseEntity(String secondCourseTitle, String course, List<String> reservationDay, int runningTime, int cost,
                              FileManagerEntity thumbnailImage, String descriptionImage, Date startDate, Date endDate,
                              String address, String category, String costOption, String link) {

        this.secondCourseTitle = secondCourseTitle;
        this.course = course;
        this.reservationDay = reservationDay;
        this.runningTime = runningTime;
        this.cost = cost;
        this.thumbnailImage = thumbnailImage;
        this.descriptionImage = descriptionImage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.category = category;
        this.costOption = costOption;
        this.link = link;
    }

    public void setThumbnailImage(FileManagerEntity thumbnailImage) {this.thumbnailImage = thumbnailImage;}

    public void updateSecondCourseTitle(String secondCourseTitle) {this.secondCourseTitle = secondCourseTitle;}

    public void updateStartDate(Date startDate) {this.startDate = startDate;}

    public void updateEndDate(Date endDate) {this.endDate = endDate;}

    public void updateCourse(String course) {this.course = course;}

    public void updateAddress(String address) {this.address = address;}

    public void updateCategory(String category) {this.category = category;}

    public void updateRunningTime(int runningTime) {this.runningTime = runningTime;}

    public void updateReservationDay(List<String> reservationDay) {this.reservationDay = reservationDay;}

    public void updateCost(int cost) {this.cost = cost;}

    public void updateCostOption(String costOption) {this.costOption = costOption;}

    public void updateThumbNailImage(FileManagerEntity thumbnailImage) {this.thumbnailImage = thumbnailImage;}

    public void updateDescriptionImage(String descriptionImage) {this.descriptionImage = descriptionImage;}

    public void updateLink(String link) {this.link = link;}

    public void deleteSecondCourse() {this.withdrawal = true;}

}
