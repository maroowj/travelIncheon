package com.field.muzi.repository;

import com.field.muzi.domain.entity.FirstCourseEntity;
import com.field.muzi.domain.entity.SecondCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondCourseRepository extends JpaRepository<SecondCourseEntity, String>, SecondCourseQueryRepository {

    SecondCourseEntity findBySecondCourseSeq(String secondCourseSeq);
}
