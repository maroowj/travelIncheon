package com.field.muzi.repository;

import com.field.muzi.domain.entity.DiyEntity;
import com.field.muzi.domain.entity.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, String>, FormQueryRepository  {
}
