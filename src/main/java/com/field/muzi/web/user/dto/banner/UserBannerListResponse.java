package com.field.muzi.web.user.dto.banner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBannerListResponse {

    private String bannerSeq;
    private String bannerTitle;
    private String bannerContents;
    private String bannerColor;
    private String reservationUrl;
    private String startDate;
    private String endDate;
    private Img bannerImage;
    private Img MobileBannerImage;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Img {
        private String fileSeq;
        private String fileName;
        private String url;
    }
}
