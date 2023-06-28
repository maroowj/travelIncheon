package com.field.muzi.repository;

import com.field.muzi.web.admin.dto.banner.BannerDetailResponse;
import com.field.muzi.web.admin.dto.banner.BannerListResponse;
import com.field.muzi.web.user.dto.banner.UserBannerListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface BannerQueryRepository {

    List<UserBannerListResponse> userBannerList();

    BannerDetailResponse adminBannerDetail(String bannerSeq);
    Page<BannerListResponse> adminBannerList(Pageable pageable);
}
