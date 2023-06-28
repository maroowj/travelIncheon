package com.field.muzi.web.common.service;


import com.field.muzi.repository.MemberInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberInfoRepository memberInfoRepository;
}
