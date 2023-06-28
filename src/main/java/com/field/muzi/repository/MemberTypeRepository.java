package com.field.muzi.repository;

import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.domain.entity.MemberTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberTypeRepository extends JpaRepository<MemberTypeEntity, String>, MemberTypeQueryRepository {

    //소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하기 위한 메소드
    Optional<MemberTypeEntity> findByMember(MemberEntity member);
}
