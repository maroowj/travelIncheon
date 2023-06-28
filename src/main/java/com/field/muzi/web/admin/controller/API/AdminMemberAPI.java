package com.field.muzi.web.admin.controller.API;

import com.field.muzi.web.admin.dto.member.MemberDetailResponse;
import com.field.muzi.web.admin.dto.member.MemberDropRequest;
import com.field.muzi.web.admin.dto.member.MemberListRequest;
import com.field.muzi.web.admin.dto.member.MemberListResponse;
import com.field.muzi.web.admin.service.AdminMemberService;
import javassist.runtime.Desc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminMemberAPI {

    private final AdminMemberService adminMemberService;

    @GetMapping("/member")
    public Page<MemberListResponse> memberList(@PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable, MemberListRequest request) {
        return adminMemberService.memberList(pageable, request);
    }

    @GetMapping("/member/{memberSeq}")
    public MemberDetailResponse memberDetail(@PathVariable("memberSeq")String memberSeq) {
        return adminMemberService.memberDetail(memberSeq);
    }

    @PutMapping("/member/drop")
    public void dropMember(@RequestBody MemberDropRequest request) {
        adminMemberService.dropMember(request.getDropList());
    }
}
