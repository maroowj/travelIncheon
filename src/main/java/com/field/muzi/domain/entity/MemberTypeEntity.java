package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.SeqManager;
import com.field.muzi.domain.user.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table (name = "memberType")
@DynamicInsert
@DynamicUpdate
@Entity
public class MemberTypeEntity {

	// strategy 수정 후 사용
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_member_type")
	@GenericGenerator(name = "seq_manager_member_type", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
			@org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "mrol_"),
			@org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
	})
	@Column(nullable = false, updatable = false, length = 15)
	@Id
	private String memberTypeSeq;

	@OneToOne
	@JoinColumn(name = "memberSeq")
	private MemberEntity member;

	@Column(nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private Role memberType;

	@Builder
	public MemberTypeEntity(MemberEntity member, Role memberType) {
		this.member = member;
		this.memberType = memberType;
	}

}