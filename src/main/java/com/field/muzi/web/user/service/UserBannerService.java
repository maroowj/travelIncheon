package com.field.muzi.web.user.service;

import com.field.muzi.repository.BannerRepository;
import com.field.muzi.web.user.dto.banner.UserBannerListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBannerService {

    private final BannerRepository bannerRepository;

    @Transactional
    public List<UserBannerListResponse> userBannerList(){
        return bannerRepository.userBannerList();
    }

}
