package com.field.muzi.domain.listener;

import com.field.muzi.setup.FileManagerEntity;
import com.field.muzi.utils.FileManagerUtils;

import javax.persistence.PreRemove;


public class FileListener {

    @PreRemove
    public void preRemove(FileManagerEntity fileManager) {
        FileManagerUtils.delete(fileManager);
    }
}
