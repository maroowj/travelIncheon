package com.field.muzi.repository;


import com.field.muzi.domain.entity.DiyEntity;
import com.field.muzi.web.user.dto.diy.DiyDetailsResponse;

import java.util.List;

public interface DiyDetailsQueryRepository {
    List<DiyDetailsResponse.Details> diyDetails(DiyEntity diy);
}
