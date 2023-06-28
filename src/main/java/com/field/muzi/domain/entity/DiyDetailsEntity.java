package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.BaseTimeEntity;
import com.field.muzi.domain.base.SeqManager;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(value = AccessLevel.PROTECTED)
@Table(name = "diyDetails")
@DynamicInsert
@DynamicUpdate
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class DiyDetailsEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_diy_details")
    @GenericGenerator(name = "seq_manager_diy_details", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
            @org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "diyd_"),
            @org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
    })
    @Column(nullable = false, updatable = false, length = 15)
    @Id
    private String diyDetailsSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diySeq")
    private DiyEntity diy;

    @Type(type = "json")
    @Column(columnDefinition = "JSON")
    private Map<String, Object> content;

    @Column(length = 200)
    private String etc;

    public static DiyDetailsEntity create(DiyEntity diy, Map<String, Object> content, String etc) {
        DiyDetailsEntity diyDetails = new DiyDetailsEntity();
        diyDetails.setDiy(diy);
        diyDetails.setContent(content);
        diyDetails.setEtc(etc);
        return diyDetails;
    }

    public void update(Map<String, Object> content, String etc) {
        setContent(content);
        setEtc(etc);
    }
}
