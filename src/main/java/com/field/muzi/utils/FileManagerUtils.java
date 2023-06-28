package com.field.muzi.utils;

import com.field.muzi.setup.FileManagerEntity;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileManagerUtils {

    public static void delete(FileManagerEntity fileManager) {
        File file = file(fileManager);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void delete(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean equals(FileManagerEntity fileManager1, FileManagerEntity fileManager2) {

        if(ObjectUtils.isEmpty(fileManager1) || ObjectUtils.isEmpty(fileManager2)) return false;

        FileInputStream in1;
        FileInputStream in2;

        try {
            in1 = new FileInputStream(file(fileManager1));
            in2 = new FileInputStream(file(fileManager2));

            boolean ret = IOUtils.contentEquals(in1, in2);

            in1.close();
            in2.close();

            return ret;

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    public static File file(FileManagerEntity fileManager) {
        return new File(new File("").getAbsolutePath() + fileManager.getDirManager().getDirUrl() + "/" + fileManager.getFileName());
    }
}
