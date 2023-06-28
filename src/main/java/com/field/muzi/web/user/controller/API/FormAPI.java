package com.field.muzi.web.user.controller.API;

import com.field.muzi.web.user.dto.form.FormDetailsResponse;
import com.field.muzi.web.user.dto.form.FormListResponse;
import com.field.muzi.web.user.dto.form.FormSaveRequest;
import com.field.muzi.web.user.dto.form.FormUpdateRequest;
import com.field.muzi.web.user.service.FormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class FormAPI {

    private final FormService formService;

    @GetMapping("/form")
    public Page<FormListResponse> formList(@PageableDefault(sort = "formSeq", direction = Sort.Direction.DESC)Pageable pageable) {
        return formService.formList(pageable);
    }

    @GetMapping("/form/{formSeq}")
    public FormDetailsResponse formDetails(@PathVariable("formSeq") String formSeq) {
        return formService.formDetails(formSeq);
    }

    @PostMapping("/form")
    public void createForm(FormSaveRequest request) throws Exception {
        formService.createForm(request);
    }

    @PutMapping("/form/{formSeq}")
    public void updateForm(FormUpdateRequest request, @PathVariable("formSeq") String formSeq) throws Exception {
        formService.updateForm(request, formSeq);
    }

    @DeleteMapping("/form/{formSeq}")
    public void deleteForm(@PathVariable("formSeq") String formSeq) {
        formService.deleteForm(formSeq);
    }
}
