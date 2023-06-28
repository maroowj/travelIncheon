package com.field.muzi.repository.setup;

import com.field.muzi.setup.DirManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirManagerRepository extends JpaRepository<DirManagerEntity, String> {
    Optional<DirManagerEntity> findByDirName(String dirName);
}
