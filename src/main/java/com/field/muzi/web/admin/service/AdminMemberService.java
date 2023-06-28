package com.field.muzi.web.admin.service;

import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.repository.MemberInfoRepository;
import com.field.muzi.repository.MemberRepository;
import com.field.muzi.utils.EntityUtils;
import com.field.muzi.web.admin.dto.member.MemberDetailResponse;
import com.field.muzi.web.admin.dto.member.MemberListRequest;
import com.field.muzi.web.admin.dto.member.MemberListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final MemberRepository memberRepository;

    public Page<MemberListResponse> memberList(Pageable pageable, MemberListRequest request) {
        Page<MemberListResponse> result = memberRepository.memberList(pageable, request);

        int pageSize = result.getSize();
        int page = result.getNumber();
        int totalCount = (int) result.getTotalElements();

        int dec = page * pageSize;

        for(MemberListResponse response : result.getContent()) {
            response.setRowNum(totalCount-dec);
            dec++;
        }
        return result;
    }

    public MemberDetailResponse memberDetail(String memberSeq) {
        MemberEntity member = EntityUtils.memberThrowable(memberRepository, memberSeq);
        return new MemberDetailResponse(member);
    }

    @Transactional
    public void dropMember(List<String> memberSeqList) {
        for(String memberSeq : memberSeqList) {
            MemberEntity member = EntityUtils.memberThrowable(memberRepository, memberSeq);
            member.dropMember();
        }
    }
}
