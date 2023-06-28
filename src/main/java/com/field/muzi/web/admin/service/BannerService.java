package com.field.muzi.web.admin.service;

import com.field.muzi.domain.entity.BannerEntity;
import com.field.muzi.repository.BannerRepository;
import com.field.muzi.repository.setup.FileManagerRepository;
import com.field.muzi.setup.FileManagerEntity;
import com.field.muzi.utils.handler.FileHandler;
import com.field.muzi.web.admin.dto.banner.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;
    private final FileManagerRepository fileManagerRepository;
    private final FileHandler fileHandler;

    /** 배너 추가 **/
    @Transactional
    public void bannerInsert(BannerSaveRequest request) throws Exception {
        FileManagerEntity bannerImage;

        if(request.getBannerImage() != null && !request.getBannerImage().isEmpty()) {
            List<MultipartFile> bannerList = new ArrayList<>();
            bannerList.add(request.getBannerImage());
            bannerImage = fileManagerRepository.save(fileHandler.parse(bannerList, "banner_img").get(0));
        }else{
            bannerImage = null;

        }

        FileManagerEntity mobileBannerImage;
        if(request.getMobileBannerImage() != null && !request.getMobileBannerImage().isEmpty()) {
            List<MultipartFile> bannerList = new ArrayList<>();
            bannerList.add(request.getMobileBannerImage());
            mobileBannerImage = fileManagerRepository.save(fileHandler.parse(bannerList, "banner_img").get(0));
        }else{
            mobileBannerImage = null;
        }

        boolean bannerStatus;
        if(request.getBannerStatus().equals("true")){
            bannerStatus=true;
        }else{
            bannerStatus=false;
        }

        BannerEntity banner = bannerRepository.save(
                BannerEntity.builder()
                        .bannerTitle(request.getBannerTitle())
                        .bannerContents(request.getBannerContents())
                        .bannerColor(request.getBannerColor())
                        .reservationUrl(request.getReservationUrl())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .bannerStatus(bannerStatus)
                        .bannerImage(bannerImage)
                        .mobileBannerImage(mobileBannerImage)
                        .build()
        );
    }


    /** 배너 상세 정보 조회 **/
    @Transactional
    public BannerDetailResponse bannerDetail(String bannerSeq) {
        return bannerRepository.adminBannerDetail(bannerSeq);
    }

    /** 배너 목록 조회 **/
    @Transactional
    public Page<BannerListResponse> bannerList(Pageable pageable) {

        Page<BannerListResponse> result = bannerRepository.adminBannerList(pageable);

        List<BannerListResponse> list = result.getContent();

        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int totalCount = (int) result.getTotalElements();

        int dec = pageSize * pageNumber;

        for (BannerListResponse response : result.getContent()) {
            response.setRowNum(totalCount - dec);
            dec++;
        }

        return result;

    }

    /** 선택한 배너 삭제 **/
    @Transactional
    public void deleteBanner(BannerDeleteRequest request) {
        for(String bannerSeq:request.getBannerSeqList()) {
            BannerEntity banner = bannerRepository.findById(bannerSeq).orElseThrow(() -> new RuntimeException("배너 정보를 찾을 수 없습니다."));
            bannerRepository.delete(banner);
        }
    }

    /** 배너 수정 **/
    @Transactional
    public void updateBanner(BannerUpdateRequest request) {
        BannerEntity banner = bannerRepository.findById(request.getBannerSeq()).orElseThrow(() -> new RuntimeException("배너 정보를 찾을 수 없습니다."));

        if(request.getBannerTitle()!=null && !request.getBannerTitle().equals(banner.getBannerTitle())) {
            banner.setBannerTitle(request.getBannerTitle());
        }

        if(request.getBannerContents()!=null && !request.getBannerContents().equals(banner.getBannerContents())) {
            banner.setBannerContents(request.getBannerContents());
        }

        if(request.getBannerColor()!=null && !request.getBannerColor().equals(banner.getBannerColor())) {
            banner.setBannerColor(request.getBannerColor());
        }

        if(request.getReservationUrl()!=null && !request.getReservationUrl().equals(banner.getReservationUrl())) {
            banner.setReservationUrl(request.getReservationUrl());
        }

        if(request.getStartDate()!=null && !request.getStartDate().equals(banner.getStartDate())) {
            banner.setStartDate(request.getStartDate());
        }

        if(request.getEndDate()!=null && !request.getEndDate().equals(banner.getEndDate())) {
            banner.setEndDate(request.getEndDate());
        }

        boolean bannerStatus;
        if(request.getBannerStatus().equals("true")){
            bannerStatus=true;
        }else{
            bannerStatus=false;
        }

        if(bannerStatus != banner.isBannerStatus()) {
            banner.setBannerStatus(bannerStatus);
        }

        if (request.getBannerImage() != null && !request.getBannerImage().isEmpty()) {
            List<MultipartFile> newBannerImages = new ArrayList<>();
            newBannerImages.add(request.getBannerImage());
            try {
                FileManagerEntity img = fileManagerRepository.save(fileHandler.parse(newBannerImages, "banner_img").get(0));

                banner.setBannerImage(img);
            } catch (Exception e) {
                throw new RuntimeException("이미지 수정에 실패했습니다.");
            }
        }
        if (request.getMobileBannerImage() != null && !request.getMobileBannerImage().isEmpty()) {
            List<MultipartFile> newBannerImages = new ArrayList<>();
            newBannerImages.add(request.getMobileBannerImage());
            try {
                FileManagerEntity img = fileManagerRepository.save(fileHandler.parse(newBannerImages, "banner_img").get(0));

                banner.setMobileBannerImage(img);
            } catch (Exception e) {
                throw new RuntimeException("이미지 수정에 실패했습니다.");
            }
        }

    }

}
