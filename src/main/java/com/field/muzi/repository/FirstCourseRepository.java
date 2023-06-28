package com.field.muzi.repository;

import com.field.muzi.domain.entity.CompanyEntity;
import com.field.muzi.domain.entity.FirstCourseEntity;
import com.field.muzi.web.user.dto.course.AllCourseListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FirstCourseRepository extends JpaRepository<FirstCourseEntity, String>, FirstCourseQueryRepository {

    FirstCourseEntity findByFirstCourseSeq(String firstCourseSeq);
}
