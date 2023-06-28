package com.field.muzi.repository;

import com.field.muzi.domain.entity.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<BannerEntity, String>, BannerQueryRepository{
}
