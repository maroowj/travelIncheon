package com.field.muzi.web.admin.dto.banner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BannerUpdateRequest {

    private String bannerSeq;
    private String bannerTitle;
    private String bannerContents;
    private String bannerColor;
    private String reservationUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String bannerStatus;
    private MultipartFile bannerImage;
    private MultipartFile mobileBannerImage;
}
