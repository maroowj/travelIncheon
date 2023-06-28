package com.field.muzi.repository;

import com.field.muzi.domain.entity.DiyDetailsEntity;
import com.field.muzi.domain.entity.DiyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiyDetailsRepository extends JpaRepository<DiyDetailsEntity, String>, DiyDetailsQueryRepository  {

    List<DiyDetailsEntity> findAllByDiy(DiyEntity diy);
    void deleteAllByDiy(DiyEntity diy);
}
