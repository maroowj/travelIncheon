package com.field.muzi.web.common.service;


import com.field.muzi.config.security.JwtProvider;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.domain.refreshtoken.RefreshToken;
import com.field.muzi.domain.refreshtoken.RefreshTokenRepository;
import com.field.muzi.domain.user.Role;
import com.field.muzi.repository.MemberInfoRepository;
import com.field.muzi.repository.MemberRepository;
import com.field.muzi.utils.EntityUtils;
import com.field.muzi.utils.enums.SocialType;
import com.field.muzi.web.client.dto.SocialProfile;
import com.field.muzi.web.common.dto.TokenRequest;
import com.field.muzi.web.common.dto.TokenResponse;
import com.field.muzi.web.common.dto.member.LoginRequest;
import com.field.muzi.web.common.dto.member.SignupRequest;
import com.field.muzi.web.exception.business.CEntityNotFoundException;
import com.field.muzi.web.exception.business.CInvalidValueException;
import com.field.muzi.web.exception.security.CTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberEntity signupAdmin(SignupRequest req) {
        MemberEntity member = MemberEntity.create(req.getMemberId(), passwordEncoder.encode(req.getMemberPwd()), req.getProvider());
        if (memberRepository.existsByMemberId(member.getMemberId())) {
            throw new CInvalidValueException.CAlreadySignedupException();
        }
        return memberRepository.save(member);
    }

    /*@Transactional
    public MemberEntity signup(SignupRequest req) {
        Member recommender = null;

        // 추천인을 입력 했을 때 존재여부 검증
        if (req.getRecommender()!=null && !req.getRecommender().equals("")) {
            recommender = memberRepository.findByUserId(req.getRecommender()).orElseThrow(CEntityNotFoundException.CRecommenderNotFoundException::new);
        }

        // 회원 번호 생성
        String membershipNumber = null;
        Optional<UserDetails> lastUser = userDetailsRepository.findTopByOrderByMembershipNumberDesc();
        if(lastUser.isPresent()) { // 마지막 회원의 번호 + 1
            membershipNumber = lastUser.get().getMembershipNumber();
            int numbering = Integer.parseInt(membershipNumber) + 1;
            membershipNumber = Integer.toString(numbering);
            if(membershipNumber.length()==1) {
                membershipNumber = "000"+membershipNumber;
            }else if(membershipNumber.length()==2) {
                membershipNumber = "00"+membershipNumber;
            }else if(membershipNumber.length()==3) {
                membershipNumber = "0"+membershipNumber;
            }
        }else { // 첫번 째 회원
            membershipNumber = "0001";
        }

        // 추천인 코드 생성 (대/소문자 + 숫자 조합 5자리)
        String recommendCode = generatedRandomKey();
        Optional<UserDetails> findRecommendCode = userDetailsRepository.findByRecommendCode(recommendCode);
        if(findRecommendCode.isPresent()) {
            while (!findRecommendCode.isPresent()){
                recommendCode = generatedRandomKey();
                findRecommendCode = userDetailsRepository.findByRecommendCode(recommendCode);
            }
        }

        // 회원 등급 불러오기 (준회원)
        Grade foundGrade = EntityUtils.gradeThrowable(gradeRepository, 1); // 준회원

        User user = User.createDirect(req.getId(), req.getPassword(), req.getProvider(), req.getUsername(),
                                        req.getBirth(), req.getMobile(), req.getEmail(), req.getAddress(),
                                        recommender, recommendCode, membershipNumber, foundGrade);
        if (memberRepository.existsByUserId(user.getUserId())) {
            throw new CAlreadySignedupException();
        }

        return memberRepository.save(user);
    }*/

/*    @Transactional
    public ManagerUsers signupManager(ManagerSaveRequest req, EFileManager profilePicture) {

        // 회원 번호 생성 (관리자는 전부 0000)
        String membershipNumber = "0000";


        // 추천인 코드 생성 (대/소문자 + 숫자 조합 5자리)
        String recommendCode = generatedRandomKey();
        Optional<UserDetails> findRecommendCode = userDetailsRepository.findByRecommendCode(recommendCode);
        if(findRecommendCode.isPresent()) {
            while (!findRecommendCode.isPresent()){
                recommendCode = generatedRandomKey();
                findRecommendCode = userDetailsRepository.findByRecommendCode(recommendCode);
            }
        }

        // 회원 등급 불러오기 (준회원)
        Grade foundGrade = EntityUtils.gradeThrowable(gradeRepository, 1); // 준회원

        User user = User.createManager(req.getId(), req.getPassword(), req.getName(),
                    req.getMobile(), recommendCode, membershipNumber, foundGrade);
        if (memberRepository.existsByUserId(user.getUserId())) {
            throw new CAlreadySignedupException();
        }

        // 관리자 분류(등급) 불러오기
        ManagerKinds foundManagerKinds = EntityUtils.managerKindsThrowable(managerKindsRepository, req.getManagerKindsSeq());

        ManagerUsers managerUsers = ManagerUsers.create(user, req.getNickName(), foundManagerKinds, profilePicture);

        memberRepository.save(user);
        return managerUsersRepository.save(managerUsers);
    }*/

/*
    @Transactional
    public ManagerUsers signupDefaultManager(ManagerSaveRequest req) {

        // 회원 번호 생성 (관리자는 전부 0000)
        String membershipNumber = "0000";


        // 추천인 코드 생성 (대/소문자 + 숫자 조합 5자리)
        String recommendCode = generatedRandomKey();
        Optional<UserDetails> findRecommendCode = userDetailsRepository.findByRecommendCode(recommendCode);
        if(findRecommendCode.isPresent()) {
            while (!findRecommendCode.isPresent()){
                recommendCode = generatedRandomKey();
                findRecommendCode = userDetailsRepository.findByRecommendCode(recommendCode);
            }
        }

        // 회원 등급 불러오기 (준회원)
        Grade foundGrade = EntityUtils.gradeThrowable(gradeRepository, 1); // 준회원

        User user = User.createManager(req.getId(), req.getPassword(), req.getName(),
                req.getMobile(), recommendCode, membershipNumber, foundGrade);
        if (memberRepository.existsByUserId(user.getUserId())) {
            throw new CAlreadySignedupException();
        }

        // 관리자 분류(등급) 불러오기
        ManagerKinds foundManagerKinds = EntityUtils.managerKindsThrowable(managerKindsRepository, req.getManagerKindsSeq());

        ManagerUsers managerUsers = ManagerUsers.create(user, req.getNickName(), foundManagerKinds, null);

        memberRepository.save(user);
        return managerUsersRepository.save(managerUsers);
    }
*/




    @Transactional
    public void socialSignup(SocialProfile socialProfile, SocialType socialType) {
        memberRepository.findBySnsIdAndProvider(socialProfile.getEmail(), socialType.name().toLowerCase())
                .ifPresent(user -> {
                    throw new CInvalidValueException.CAlreadySignedupException();
                });
        memberRepository.save(
                MemberEntity.createSocial(
                        socialType.name().toLowerCase() + RandomStringUtils.random(15, true, true),
                        passwordEncoder.encode(UUID.randomUUID().toString()),
                        socialType.name().toLowerCase(),
                        socialProfile.getEmail()
                )
        );
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        MemberEntity member = memberRepository.findByMemberId(request.getMemberId()).orElseThrow(CInvalidValueException.CLoginFailedException::new);
        if (!passwordEncoder.matches(request.getMemberPwd(), member.getPassword())) {
            throw new CInvalidValueException.CLoginFailedException();
        }
        if (member.isWithdrawal()) {
            throw new CInvalidValueException.CLoginFailedException();
        }
        refreshTokenRepository.findByKey(member.getMemberSeq()).ifPresent(refreshTokenRepository::delete);
        TokenResponse tokenResponse = jwtProvider.createToken(member.getMemberSeq(), member.getRoles().stream().map(Role::getValue).collect(Collectors.toList()));
        refreshTokenRepository.save(RefreshToken.create(member.getMemberSeq(), tokenResponse.getRefreshToken()));
        return tokenResponse;
    }

    @Transactional
    public TokenResponse socialLogin(LoginRequest request) {
        MemberEntity member = memberRepository.findBySnsIdAndProvider(request.getMemberId(), request.getProvider())
                .orElseThrow(CEntityNotFoundException.CUserNotFoundException::new);
        refreshTokenRepository.findByKey(member.getMemberSeq()).ifPresent(refreshTokenRepository::delete);
        TokenResponse tokenResponse = jwtProvider.createToken(member.getMemberSeq(), member.getRoles().stream().map(Role::getValue).collect(Collectors.toList()));
        refreshTokenRepository.save(RefreshToken.create(member.getMemberSeq(), tokenResponse.getRefreshToken()));
        return tokenResponse;
    }

    /**
     * TokenRequest를 통해 액세스 토큰 재발급 요청
     * * 리프레시 토큰 만료 검증 -> 만료 시 재로그인 요청
     */
    @Transactional
    public TokenResponse reissue(TokenRequest request) {

        //리프레시 토큰 만료
        if (!jwtProvider.validationToken(request.getRefreshToken())) {
            throw new CTokenException.CRefreshTokenException();
        }

        String accessToken = request.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        MemberEntity foundMember = EntityUtils.memberThrowable(memberRepository, ((MemberEntity) authentication.getPrincipal()).getMemberSeq());

        //리프레시 토큰 없음
        RefreshToken refreshToken = refreshTokenRepository.findByKey(foundMember.getMemberSeq())
                .orElseThrow(CTokenException.CRefreshTokenException::new);

        //리프레시 토큰 불일치
        if (!refreshToken.getToken().equals(request.getRefreshToken())) {
            throw new CTokenException.CRefreshTokenException();
        }

        TokenResponse newCreatedToken = jwtProvider.createToken(foundMember.getMemberSeq(), foundMember.getRoles().stream().map(Role::getValue).collect(Collectors.toList()));
        refreshToken.update(newCreatedToken.getRefreshToken());

        return newCreatedToken;
    }

/*    @Transactional
    public boolean duplicateCheckId(DuplicateCheckIdRequest request) {
        return memberRepository.existsByUserId(request.getId());
    }*/

    // 추천인 코드 생성 메소드
    private static String generatedRandomKey() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}
