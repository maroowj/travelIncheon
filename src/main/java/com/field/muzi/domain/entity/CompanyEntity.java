package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.SeqManager;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "company")
@DynamicInsert
@DynamicUpdate
@Entity
public class CompanyEntity {

    // strategy 수정 후 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_company")
    @GenericGenerator(name = "seq_manager_company", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
            @org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "comp_"),
            @org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
    })
    @Column(nullable = false, updatable = false, length = 15)
    @Id
    private String companySeq;

    @Column(nullable = false, length = 200)
    private String companyTitle;

    @Column(nullable = false, length = 100)
    private String companyColor;

    @Column(columnDefinition = "TEXT")
    private String companyDescription;

    @Column(length = 300)
    private String companyAddress;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "datetime default now()")
    private Date createDate;

    @Column
    @UpdateTimestamp
    private Date updateDate;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean withdrawal;

    @Builder
    public CompanyEntity(String companyTitle, String companyColor, String companyDescription, String companyAddress) {
        this.companyTitle = companyTitle;
        this.companyColor = companyColor;
        this.companyDescription = companyDescription;
        this.companyAddress = companyAddress;
    }

    public void deleteCompany() {
        this.withdrawal = true;
    }

    public void updateCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }

    public void updateCompanyColor(String companyColor) {
        this.companyColor = companyColor;
    }

    public void updateCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
}
