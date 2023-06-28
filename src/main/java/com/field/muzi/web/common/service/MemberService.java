package com.field.muzi.web.common.service;

import com.field.muzi.repository.MemberRepository;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.domain.entity.MemberTypeEntity;
import com.field.muzi.repository.MemberTypeRepository;
import com.field.muzi.web.exception.business.CEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberTypeRepository memberTypeRepository;

    @Transactional
    public MemberEntity findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CEntityNotFoundException.CUserNotFoundException());
    }

    @Transactional
    public MemberTypeEntity signup(MemberTypeEntity memberTypeEntity) {
        memberRepository.save(memberTypeEntity.getMember());
        return memberTypeRepository.save(memberTypeEntity);
    }
}
