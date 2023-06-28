package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.BaseTimeEntity;
import com.field.muzi.domain.base.SeqManager;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(value = AccessLevel.PROTECTED)
@Table(name = "diy")
@DynamicInsert
@DynamicUpdate
@Entity
public class DiyEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_diy")
    @GenericGenerator(name = "seq_manager_diy", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
            @org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "diys_"),
            @org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
            @org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
    })
    @Column(nullable = false, updatable = false, length = 15)
    @Id
    private String diySeq;

    @Column(nullable = false, length = 50)
    private String diyTitle;

    @OneToOne
    @JoinColumn(name = "memberSeq")
    private MemberEntity member;

    @Column(columnDefinition="timestamp(6)")
    private Date startDate;

    @Column(columnDefinition="timestamp(6)")
    private Date endDate;

    @Column(columnDefinition = "text")
    private String content;

    @Column(length = 200)
    private String etc;

    @Column(columnDefinition="timestamp(6)")
    private Date deletedAt;

    @Column(columnDefinition="timestamp(6)")
    private Date confirmedAt;

    public static DiyEntity create(String diyTitle, MemberEntity member, Date startDate, Date endDate,
                                   String content, String etc) {
        DiyEntity diy = new DiyEntity();
        diy.setDiyTitle(diyTitle);
        diy.setMember(member);
        diy.setStartDate(startDate);
        diy.setEndDate(endDate);
        diy.setContent(content);
        diy.setEtc(etc);
        return diy;
    }

    public void update(String diyTitle, Date startDate, Date endDate,
                       String content, String etc) {
        setDiyTitle(diyTitle);
        setStartDate(startDate);
        setEndDate(endDate);
        setContent(content);
        setEtc(etc);
    }

    public void delete() {
        setDeletedAt(new Date());
    }

    public void confirm() {
        setConfirmedAt(new Date());
    }
}
