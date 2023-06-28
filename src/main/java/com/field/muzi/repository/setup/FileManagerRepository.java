package com.field.muzi.repository.setup;

import com.field.muzi.setup.FileManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileManagerRepository extends JpaRepository<FileManagerEntity, String> {
}
