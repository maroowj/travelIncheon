package com.field.muzi.repository;

import com.field.muzi.domain.entity.DiyEntity;
import com.field.muzi.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiyRepository extends JpaRepository<DiyEntity, String>, DiyQueryRepository  {

}
