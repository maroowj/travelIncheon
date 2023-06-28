package com.field.muzi.web.user.controller.API;

import com.field.muzi.web.user.dto.banner.UserBannerListResponse;
import com.field.muzi.web.user.service.UserBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserBannerAPI {

    private final UserBannerService userBannerService;

    @GetMapping("/banner/list")
    public List<UserBannerListResponse> userBannerList() {
        return userBannerService.userBannerList();
    }

}
