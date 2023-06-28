package com.field.muzi.repository;


import com.field.muzi.domain.entity.DiyEntity;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.web.user.dto.diy.DiyDetailsResponse;
import com.field.muzi.web.user.dto.diy.DiyListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiyQueryRepository {

    Page<DiyListResponse> diyListByMember(Pageable pageable, MemberEntity member);
    DiyDetailsResponse.Diy diy(DiyEntity diy);
}
