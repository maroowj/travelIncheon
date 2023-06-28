package com.field.muzi.web.user.service;

import com.field.muzi.domain.entity.DiyDetailsEntity;
import com.field.muzi.domain.entity.DiyEntity;
import com.field.muzi.domain.entity.DiyOptionsEntity;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.repository.DiyDetailsRepository;
import com.field.muzi.repository.DiyOptionsRepository;
import com.field.muzi.repository.DiyRepository;
import com.field.muzi.repository.MemberRepository;
import com.field.muzi.utils.EntityUtils;
import com.field.muzi.web.exception.business.CEntityNotFoundException;
import com.field.muzi.web.user.dto.diy.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiyService {

    private final MemberRepository memberRepository;
    private final DiyRepository diyRepository;
    private final DiyDetailsRepository diyDetailsRepository;
    private final DiyOptionsRepository diyOptionsRepository;

    // diy 테이블 생성
    @Transactional
    public DiyEntity createDiy(DiySaveRequest request) {
        MemberEntity member = EntityUtils.memberThrowable(memberRepository);
        if (member == null) {
            throw new CEntityNotFoundException.CUserNotFoundException();
        }
        return diyRepository.save(
                DiyEntity.create(
                        request.getDiyTitle(),
                        member,
                        request.getStartDate(),
                        request.getEndDate(),
                        request.getContent(),
                        request.getEtc()
                )
        );
    }

    // diy_details 테이블 생성
    @Transactional
    public void createDiyDetails(List<DiyDetailsSaveRequest> requestList) {
        List<DiyDetailsEntity> saveList = new ArrayList<>();

        DiyEntity diy = null;

        if(requestList!=null && requestList.size()!=0) {
            diy = EntityUtils.diyThrowable(diyRepository, requestList.get(0).getDiySeq());
        }

        List<DiyDetailsEntity> foundDetailsList = diyDetailsRepository.findAllByDiy(diy);

        if(diy!=null && foundDetailsList!=null && foundDetailsList.size()!=0) {
            diyDetailsRepository.deleteAllByDiy(diy);
        }

        for (DiyDetailsSaveRequest request : requestList) {
            DiyDetailsEntity diyDetails = DiyDetailsEntity.create(
                    diy,
                    request.getContent(),
                    request.getEtc()
            );
            saveList.add(diyDetails);
        }
        diyDetailsRepository.saveAll(saveList);
    }

    // diy_options 테이블 생성
    @Transactional
    public void createDiyOptions(List<DiyOptionsSaveRequest> requestList) {
        List<DiyOptionsEntity> saveList = new ArrayList<>();

        DiyEntity diy = null;

        if(requestList!=null && requestList.size()!=0) {
            diy = EntityUtils.diyThrowable(diyRepository, requestList.get(0).getDiySeq());
        }

        List<DiyOptionsEntity> foundOptionsList = diyOptionsRepository.findAllByDiy(diy);

        if(diy!=null && foundOptionsList!=null && foundOptionsList.size()!=0) {
            diyOptionsRepository.deleteAllByDiy(diy);
        }

        for (DiyOptionsSaveRequest request : requestList) {
            DiyOptionsEntity diyOptions = DiyOptionsEntity.create(
                    diy,
                    request.getContent(),
                    request.getEtc()
            );
            saveList.add(diyOptions);
        }
        diyOptionsRepository.saveAll(saveList);
    }

    // diy 테이블 수정
    @Transactional
    public void updateDiy(DiyUpdateRequest request) {
        DiyEntity diy = EntityUtils.diyThrowable(diyRepository, request.getDiySeq());
        diy.update(
                request.getDiyTitle(),
                request.getStartDate(),
                request.getEndDate(),
                request.getContent(),
                request.getEtc()
        );
    }

    // diy_details 테이블 수정
    @Transactional
    public void updateDiyDetails(List<DiyDetailsUpdateRequest> requestList) {
        for(DiyDetailsUpdateRequest request : requestList) {
            DiyDetailsEntity diyDetails =EntityUtils.diyDetailsThrowable(diyDetailsRepository, request.getDiyDetailsSeq());

            diyDetails.update(
                    request.getContent(),
                    request.getEtc()
            );
        }
    }

    // diy_details 테이블 수정
    @Transactional
    public void updateDiyOptions(List<DiyOptionsUpdateRequest> requestList) {
        for(DiyOptionsUpdateRequest request : requestList) {
            DiyOptionsEntity diyOptions =EntityUtils.diyOptionsThrowable(diyOptionsRepository, request.getDiyOptionsSeq());

            diyOptions.update(
                    request.getContent(),
                    request.getEtc()
            );
        }
    }

    // diy 테이블 목록
    @Transactional
    public Page<DiyListResponse> diyListByMember(Pageable pageable) {
        MemberEntity member = EntityUtils.memberThrowable(memberRepository);
        return diyRepository.diyListByMember(pageable, member);
    }

    // diy 상세
    public DiyDetailsResponse diyDetails(String diySeq) {
        DiyDetailsResponse response = new DiyDetailsResponse();
        DiyEntity diy = EntityUtils.diyThrowable(diyRepository, diySeq);
        response.setDiy(diyRepository.diy(diy));
        response.setDetails(diyDetailsRepository.diyDetails(diy));
        response.setOptions(diyOptionsRepository.diyOptions(diy));
        return response;
    }
}
