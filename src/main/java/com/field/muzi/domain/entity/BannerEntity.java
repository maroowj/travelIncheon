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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "banner")
@DynamicInsert
@DynamicUpdate
@Entity
public class BannerEntity {

    // strategy 수정 후 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_banner")
    @GenericGenerator(name = "seq_manager_banner", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
            @org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "bner_"),
            @org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
    })
    @Column(nullable = false, updatable = false, length = 15)
    @Id
    private String bannerSeq;

    @Column(nullable = false, length = 200)
    private String bannerTitle;

    @Column(nullable = false, length = 200)
    private String bannerContents;

    @Column(nullable = false, length = 100)
    private String bannerColor;

    @Column(nullable = false, length = 200)
    private String reservationUrl;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bannerImage")
    private FileManagerEntity bannerImage;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mobileBannerImage")
    private FileManagerEntity mobileBannerImage;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean bannerStatus;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "datetime default now()")
    private Date createDate;

    @Column
    @UpdateTimestamp
    private Date updateDate;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean withdrawal;

    @Builder
    public BannerEntity(String bannerTitle, String bannerContents, String bannerColor, FileManagerEntity bannerImage,
                        FileManagerEntity mobileBannerImage, String reservationUrl, Date startDate, Date endDate, boolean bannerStatus) {

        this.bannerTitle = bannerTitle;
        this.bannerContents = bannerContents;
        this.bannerColor = bannerColor;
        this.bannerImage = bannerImage;
        this.mobileBannerImage = mobileBannerImage;
        this.reservationUrl = reservationUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bannerStatus = bannerStatus;
    }

    public void deleteBanner() {
        this.withdrawal = true;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public void setBannerContents(String bannerContents) {
        this.bannerContents = bannerContents;
    }

    public void setBannerColor(String bannerColor) {
        this.bannerColor = bannerColor;
    }

    public void setReservationUrl(String reservationUrl) {
        this.reservationUrl = reservationUrl;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setBannerStatus(boolean bannerStatus) {
        this.bannerStatus = bannerStatus;
    }

    public void setBannerImage(FileManagerEntity bannerImage) {
        this.bannerImage = bannerImage;
    }

    public void setMobileBannerImage(FileManagerEntity mobileBannerImage) { this.mobileBannerImage = mobileBannerImage; }
}
