package com.field.muzi.web.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.field.muzi.domain.entity.FormEntity;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.repository.FormRepository;
import com.field.muzi.repository.MemberRepository;
import com.field.muzi.repository.setup.FileManagerRepository;
import com.field.muzi.setup.FileManagerEntity;
import com.field.muzi.utils.EntityUtils;
import com.field.muzi.utils.handler.FileHandler;
import com.field.muzi.web.user.dto.form.FormDetailsResponse;
import com.field.muzi.web.user.dto.form.FormListResponse;
import com.field.muzi.web.user.dto.form.FormSaveRequest;
import com.field.muzi.web.user.dto.form.FormUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class FormService {

    private final MemberRepository memberRepository;
    private final FormRepository formRepository;
    private final FileHandler fileHandler;
    private final FileManagerRepository fileManagerRepository;

    @Transactional
    public void createForm(FormSaveRequest request) throws Exception {
        String memberSeq = SecurityContextHolder.getContext().getAuthentication().getName();

        String accessCode = String.valueOf(System.currentTimeMillis());

        List<MultipartFile> excelList = new ArrayList<>();
        FileManagerEntity excel = null;

        if(request.getExcel() != null && !request.getExcel().isEmpty()) {
            excelList.add(request.getExcel());
            excel = fileManagerRepository.save(fileHandler.parse(excelList, "excel_file").get(0));
        }else{
            excel = null;
        }

        Map<String, Object> content = new ObjectMapper().readValue(request.getContent(), HashMap.class);
        Map<String, Object> applicant = null;
        if(request.getFormType()!=null && request.getFormType().equals("self") && !request.getApplicant().equals("") && request.getApplicant()!=null) {
            applicant = new ObjectMapper().readValue(request.getApplicant(), HashMap.class);
        }

        if(!memberSeq.equals("anonymousUser")) {
            MemberEntity member = EntityUtils.memberThrowable(memberRepository);
            formRepository.save(
                    FormEntity.create(
                            member,
                            request.getTitle(),
                            request.getCategory(),
                            content,
                            request.getEtc(),
                            request.getFormType(),
                            applicant,
                            excel,
                            accessCode
                    )
            );
        }else {
            formRepository.save(
                    FormEntity.create(
                            null,
                            request.getTitle(),
                            request.getCategory(),
                            content,
                            request.getEtc(),
                            request.getFormType(),
                            applicant,
                            excel,
                            accessCode
                    )
            );
        }
    }

    @Transactional
    public void updateForm(FormUpdateRequest request, String formSeq) throws Exception {
        FormEntity form  = EntityUtils.formThrowable(formRepository, formSeq);

        List<MultipartFile> excelList = new ArrayList<>();
        FileManagerEntity excel = null;

        if(request.getExcel() != null && !request.getExcel().isEmpty()) {
            excelList.add(request.getExcel());
            excel = fileManagerRepository.save(fileHandler.parse(excelList, "excel_file").get(0));
        }else{
            excel = null;
        }

        Map<String, Object> content = new ObjectMapper().readValue(request.getContent(), HashMap.class);
        Map<String, Object> applicant = null;
        if(request.getFormType().equals("self") && !request.getApplicant().equals("") && request.getApplicant()!=null) {
            applicant = new ObjectMapper().readValue(request.getApplicant(), HashMap.class);
        }

        form.update(
                request.getTitle(),
                content,
                request.getEtc(),
                request.getFormType(),
                applicant,
                excel
        );
    }

    @Transactional
    public Page<FormListResponse> formList(Pageable pageable) {
        MemberEntity member = EntityUtils.memberThrowable(memberRepository);
        return formRepository.formListByMember(pageable, member);
    }

    @Transactional
    public FormDetailsResponse formDetails(String formSeq) {
        FormEntity form = EntityUtils.formThrowable(formRepository, formSeq);
        String fileUrl=null;
        if(!ObjectUtils.isEmpty(form.getExcel())) {
            fileUrl = fileHandler.fileUrl(form.getExcel());
        }
        return new FormDetailsResponse(form, fileUrl);
    }

    @Transactional
    public void deleteForm(String formSeq) {
        FormEntity form  = EntityUtils.formThrowable(formRepository, formSeq);
//        form.delete();
        formRepository.delete(form);
    }

    // 추천인 코드 생성 메소드
    private static String generatedRandomKey() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}
