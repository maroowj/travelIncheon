package com.field.muzi.web.user.controller.API;

import com.field.muzi.domain.entity.DiyEntity;
import com.field.muzi.web.user.dto.diy.*;
import com.field.muzi.web.user.service.DiyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class DiyAPI {

    private final DiyService diyService;

    @GetMapping("/diy")
    public Page<DiyListResponse> diyList(@PageableDefault(sort = "diySeq", direction = Sort.Direction.DESC) Pageable pageable) {
        return diyService.diyListByMember(pageable);
    }

    @GetMapping("/diy/{diySeq}")
    public DiyDetailsResponse diyDetails(@PathVariable("diySeq") String diySeq) {
        return diyService.diyDetails(diySeq);
    }

    @PostMapping("/diy")
    public DiyEntity createDiy(@RequestBody DiySaveRequest request) {
        return diyService.createDiy(request);
    }

    @PutMapping("/diy")
    public void updateDiy(@RequestBody DiyUpdateRequest request) {
        diyService.updateDiy(request);
    }

    // diy_details 생성
    @PostMapping("/diy/details")
    public void createDiyDetails(@RequestBody List<DiyDetailsSaveRequest> request) {
        diyService.createDiyDetails(request);
    }

    // diy_options 생성
    @PostMapping("/diy/options")
    public void createDiyOptions(@RequestBody List<DiyOptionsSaveRequest> request) {
        diyService.createDiyOptions(request);
    }
}
