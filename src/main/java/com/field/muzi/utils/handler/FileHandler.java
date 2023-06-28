package com.field.muzi.utils.handler;

import com.field.muzi.FiledApplication;
import com.field.muzi.repository.MemberInfoRepository;
import com.field.muzi.repository.setup.DirManagerRepository;
import com.field.muzi.setup.DirManagerEntity;
import com.field.muzi.setup.FileManagerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Slf4j
@Component
@RequiredArgsConstructor
public class FileHandler {

    private final DirManagerRepository dirManagerRepository;
    private final FiledApplication application;

//    private String pathPrefix() throws IOException {
//        return new ClassPathResource("/static").getFile().getAbsolutePath();
//    }

    public String fileUrl(FileManagerEntity fileManager) {
        return application.hostAddress() + fileManager.getDirManager().getDirUrl() + fileManager.getFileName();
    }

    public List<FileManagerEntity> parse(List<MultipartFile> multipartFiles, String dirName) throws Exception {
//        MemberInfoEntity foundMemberInfo = memberInfoRepository.findByMemberId(SecurityContextHolder.getContext().getAuthentication().getName())
//                .orElseThrow(CMemberInfoNotFoundException::new);
//        AdminEntity foundAdmin = Optional.ofNullable(foundMemberInfo.getAdmin())
//                .orElseThrow(CGroupAccountNotFoundException::new);
//        MemberEntity foundAdminMember = Optional.ofNullable(foundAdmin.getMember())
//                .orElseThrow(CMemberNotFoundException::new);

        // 반환할 파일 리스트
        List<FileManagerEntity> fileManagerList = new ArrayList<>();

        DirManagerEntity dirManager = dirManagerRepository.findByDirName(dirName)
                .orElseThrow(RuntimeException::new);

        // 전달되어 온 파일이 존재할 경우
        if(!CollectionUtils.isEmpty(multipartFiles)) {
            // 절대 경로 설정
            String uploadPath = new File("").getAbsolutePath() + dirManager.getDirUrl();


            //String prefixPath = pathPrefix() + "/filemanager/" + foundAdminMember.getMemberId() +
            //        dirManager.getDirUrl().substring(dirManager.getDirUrl().lastIndexOf("[id]") + 4);
//            String uploadPath = pathPrefix() + "/filemanager/" + foundAdminMember.getMemberId() +
//            String uploadPath = pathPrefix() + "/filemanager/" +
//                    dirManager.getDirUrl().substring(dirManager.getDirUrl().lastIndexOf("[id]") + 4);

            log.info("uploadPath: " + uploadPath);
            //log.info("prefixPath: " + prefixPath);

            // 파일을 저장할 세부 경로 지정
            File file = new File(uploadPath);

            // 디렉터리가 존재하지 않을 경우
            if(!file.exists() && !file.mkdirs()) {
                // 디렉터리 생성에 실패했을 경우
                throw new RuntimeException("file: was not successful");
            }

            // 다중 파일 처리
            for(MultipartFile multipartFile : multipartFiles) {
                // 파일의 확장자 추출
                String originalFileExtension = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                String contentType = multipartFile.getContentType();

                // 확장자명이 존재하지 않을 경우 처리 x
                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                }

                // 파일명 중복 피하고자 밀리초까지 얻어와 지정
                String newFileName = System.currentTimeMillis() + originalFileExtension;

                // 생성 후 리스트에 추가
                fileManagerList.add(new FileManagerEntity(dirManager, newFileName, multipartFile.getOriginalFilename(), originalFileExtension, Long.valueOf(multipartFile.getSize()).intValue() / 1024, ""));

                log.info("uploadPath: "+ uploadPath + newFileName);

                // 업로드 한 파일 데이터를 지정한 파일에 저장
                file = new File(uploadPath + newFileName);
                multipartFile.transferTo(file);
                Thread.sleep(100);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);
            }
        }

        return fileManagerList;
    }

    public void drop(String fileManagerSeq) {
    }
}
