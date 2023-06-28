package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.BaseTimeEntity;
import com.field.muzi.domain.base.SeqManager;
import com.field.muzi.setup.FileManagerEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(value = AccessLevel.PROTECTED)
@Table(name = "form")
@DynamicInsert
@DynamicUpdate
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class FormEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_form")
    @GenericGenerator(name = "seq_manager_form", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
            @org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "form_"),
            @org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
    })
    @Column(nullable = false, updatable = false, length = 15)
    @Id
    private String formSeq;

    @OneToOne
    @JoinColumn(name = "memberSeq")
    private MemberEntity member;

    @Column(length = 200)
    private String title;

    @Column(length = 200)
    private String category;

    @Type(type = "json")
    @Column(columnDefinition = "JSON")
    private Map<String, Object> content;

    @Column(length = 200)
    private String etc;

    @Column(columnDefinition="timestamp(6)")
    private Date deletedDate;

    @Column(columnDefinition="timestamp(6)")
    private Date confirmedDate;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Type(type = "json")
    @Column(columnDefinition = "JSON")
    private Map<String, Object> applicant;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "excel")
    private FileManagerEntity excel;

    @Column
    private String formType;

    @Column
    private String accessCode;


    public static FormEntity create(MemberEntity member, String title, String category, Map<String, Object> content, String etc,
                                    String formType, Map<String, Object> applicant,  FileManagerEntity excel, String accessCode) {
        FormEntity form = new FormEntity();
        form.setMember(member);
        form.setTitle(title);
        form.setCategory(category);
        form.setContent(content);
        form.setEtc(etc);
        form.setFormType(formType);
        form.setApplicant(applicant);
        form.setExcel(excel);
        form.setAccessCode(accessCode);
        return form;
    }

    public void update(String title, Map<String, Object> content, String etc,
                       String formType, Map<String, Object> applicant,  FileManagerEntity excel) {
        setTitle(title);
        setContent(content);
        setEtc(etc);
        setFormType(formType);
        if(formType.equals("self")) {
            setApplicant(applicant);
            setExcel(null);
        }else {
            setApplicant(null);
            if(excel!=null) {
                setExcel(excel);
            }
        }
    }

    public void delete() {
        setDeletedDate(new Date());
    }

    public void confirm() {
        setConfirmedDate(new Date());
    }

    public void answer(String answer) {
        setConfirmedDate(new Date());
        setAnswer(answer);
    }
}
