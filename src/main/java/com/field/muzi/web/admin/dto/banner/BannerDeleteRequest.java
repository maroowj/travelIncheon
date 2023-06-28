package com.field.muzi.web.admin.dto.banner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerDeleteRequest {

    private List<String> bannerSeqList;
}
