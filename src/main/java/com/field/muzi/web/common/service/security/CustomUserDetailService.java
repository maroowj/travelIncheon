package com.field.muzi.web.common.service.security;


import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * loadUserByUsername
     *
     * @param
     * @return
     * @throws UsernameNotFoundException
     */
/*
    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return memberRepository.findById(userPk).orElseThrow(CEntityNotFoundException.CUserNotFoundException::new);
    }
*/
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findById(memberId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(memberId + " -> does not exist."));
    }

    // DB 에 USER 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(MemberEntity memberEntity) throws UsernameNotFoundException {
        String auth = "ROLE_" + String.valueOf(memberEntity.getRoles().get(0)); // 221024 우람 추가
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(memberEntity.getAuthorities().toString());
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(auth);
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(grantedAuthority);
        UserDetails userDetails =  new User(
                memberEntity.getMemberSeq(),
                memberEntity.getPassword(),
                grantedAuthorities);
        return userDetails;
    }
}
