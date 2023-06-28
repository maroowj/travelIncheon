package com.field.muzi.web.common.controller;


import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;

@Slf4j
@RestController
@RequestMapping("/filemanager")
@RequiredArgsConstructor
public class FileController {

//    private String pathPrefix() throws IOException {
//        return new ClassPathResource("/static").getFile().getAbsolutePath();
//    }

    @GetMapping("/**")
    public byte[] file(HttpServletRequest request) throws Exception {
        FileInputStream in = new FileInputStream(new File("").getAbsolutePath() + "/filemanager/" + request.getRequestURI()
                .split(request.getContextPath() + "/filemanager/")[1]);
        byte[] image = IOUtils.toByteArray(in);
        in.close();
        return image;
    }

}
