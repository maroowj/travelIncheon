package com.field.muzi.web.admin.dto.banner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerDetailResponse {

    private String bannerSeq;
    private String bannerTitle;
    private String bannerContents;
    private String bannerColor;
    private String reservationUrl;
    private String startDate;
    private String endDate;
    private String bannerUrl;
    private String mobileBannerUrl;
    private boolean bannerStatus;

}
