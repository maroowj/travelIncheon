package com.field.muzi.repository;

import com.field.muzi.domain.entity.DiyDetailsEntity;
import com.field.muzi.domain.entity.DiyEntity;
import com.field.muzi.domain.entity.DiyOptionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiyOptionsRepository extends JpaRepository<DiyOptionsEntity, String>, DiyOptionsQueryRepository  {

    List<DiyOptionsEntity> findAllByDiy(DiyEntity diy);
    void deleteAllByDiy(DiyEntity diy);
}
