package com.field.muzi.web.common.service;

import com.field.muzi.config.security.JwtProvider;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.domain.entity.MemberInfoEntity;
import com.field.muzi.domain.refreshtoken.RefreshToken;
import com.field.muzi.domain.refreshtoken.RefreshTokenRepository;
import com.field.muzi.domain.user.Role;
import com.field.muzi.repository.MemberInfoRepository;
import com.field.muzi.repository.MemberRepository;
import com.field.muzi.web.common.dto.KakaoSignupRequest;
import com.field.muzi.web.common.dto.TokenResponse;
import com.field.muzi.web.exception.business.CEntityNotFoundException;
import com.field.muzi.web.exception.business.CInvalidValueException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse kakaoLogin(KakaoSignupRequest request) {
        Optional<MemberEntity> optionalMember = memberRepository.findBySnsIdAndProvider(request.getSid(), request.getProvider());

        if(!optionalMember.isPresent()) {
            String memberId = "user" + System.currentTimeMillis();
            String memberPwd = passwordEncoder.encode(generatedRandomKey());
            MemberEntity member = MemberEntity.kakaoSignup(
                    memberId,
                    memberPwd,
                    request.getProvider(),
                    request.getSid(),
                    request.getNickname(),
                    request.getEmail(),
                    request.getThumbnailImageUrl(),
                    request.getAge(),
                    request.getGender(),
                    request.getBirth()
            );
            memberRepository.save(member);
        } else {
            MemberEntity member = optionalMember.get();
            MemberInfoEntity memberInfo = member.getMemberInfo();
            memberInfo.updateKaKaoProfile(
                    request.getNickname(),
                    request.getEmail(),
                    request.getThumbnailImageUrl(),
                    request.getAge(),
                    request.getGender(),
                    request.getBirth()
            );
        }

        MemberEntity member = memberRepository.findBySnsIdAndProvider(request.getSid(), request.getProvider())
                .orElseThrow(CEntityNotFoundException.CUserNotFoundException::new);
        refreshTokenRepository.findByKey(member.getMemberSeq()).ifPresent(refreshTokenRepository::delete);
        TokenResponse tokenResponse = jwtProvider.createToken(member.getMemberSeq(), member.getRoles().stream().map(Role::getValue).collect(Collectors.toList()));
        refreshTokenRepository.save(RefreshToken.create(member.getMemberSeq(), tokenResponse.getRefreshToken()));

        return tokenResponse;
    }


    private static String generatedRandomKey() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

}
