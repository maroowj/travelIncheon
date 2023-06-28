package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.BaseTimeEntity;
import com.field.muzi.domain.base.SeqManager;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(value = AccessLevel.PROTECTED)
@Table(name = "inquiry")
@DynamicInsert
@DynamicUpdate
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class InquiryEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_inquiry")
    @GenericGenerator(name = "seq_manager_inquiry", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
            @org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "inqr_"),
            @org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
    })
    @Column(nullable = false, updatable = false, length = 15)
    @Id
    private String inquirySeq;

    @OneToOne
    @JoinColumn(name = "memberSeq")
    private MemberEntity member;

    @Column(length = 200)
    private String name;

    @Column(length = 200)
    private String businessName;

    @Column(length = 200)
    private String businessType;

    @Column(length = 200)
    private String serviceType;

    @Column(length = 200)
    private String area;

    @Column(length = 200)
    private String optionRequest;

    @Column(length = 200)
    private String tel;

    @Column(length = 200)
    private String email;

    @Column(length = 200)
    private String address;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column(columnDefinition="timestamp(6)")
    private Date deletedDate;

    public static InquiryEntity create(MemberEntity member, String name, String businessName, String businessType, String serviceType,
                                       String area, String option, String tel, String email, String address, String content) {
        InquiryEntity inquiry = new InquiryEntity();
        inquiry.setMember(member);
        inquiry.setName(name);
        inquiry.setBusinessName(businessName);
        inquiry.setBusinessType(businessType);
        inquiry.setArea(area);
        inquiry.setOptionRequest(option);
        inquiry.setTel(tel);
        inquiry.setEmail(email);
        inquiry.setAddress(address);
        inquiry.setContent(content);
        inquiry.setServiceType(serviceType);
        return inquiry;
    }

    public void update(String name, String businessName, String businessType, String serviceType,
                       String area, String option, String tel, String email, String address, String content) {
        setName(name);
        setBusinessName(businessName);
        setBusinessType(businessType);
        setArea(area);
        setOptionRequest(option);
        setTel(tel);
        setEmail(email);
        setAddress(address);
        setContent(content);
        setServiceType(serviceType);
    }

    public void delete() {
        setDeletedDate(new Date());
    }

    public void answer(String answer) {
        setAnswer(answer);
    }
}
