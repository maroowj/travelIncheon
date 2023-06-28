package com.field.muzi.repository;

import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.domain.entity.MemberInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, String>, MemberInfoQueryRepository {



}
