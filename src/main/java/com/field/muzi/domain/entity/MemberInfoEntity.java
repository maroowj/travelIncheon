package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.BaseTimeEntity;
import com.field.muzi.domain.base.SeqManager;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
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
@Table (name = "memberInfo")
@DynamicInsert
@DynamicUpdate
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class MemberInfoEntity {

	// strategy 수정 후 사용
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_member_info")
	@GenericGenerator(name = "seq_manager_member_info", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
			@org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "minf_"),
			@org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
	})
	@Column(nullable = false, updatable = false, length = 15)
	@Id
	private String memberInfoSeq;

	@Column(length = 100)
	private String memberName;

	@Column(length = 100)
	private String name;

	@Column(length = 30)
	private String tel;

	@Column(length = 200)
	private String email;

	@Column(length = 255)
	private String profileImage;

	@Column(length = 30)
	private String ageRange;

	@Column(length = 20)
	private String gender;

	@Column(length = 20)
	private String birth;

	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "datetime default now()")
	private Date createDate;

	@Column
	@UpdateTimestamp
	private Date updateDate;

	@Column
	private Date deleteDate;

	@Builder
	public MemberInfoEntity(String memberName, String tel, String email) {
		this.memberName = memberName;
		this.tel = tel;
		this.email = email;
	}

	public static MemberInfoEntity create() {
		MemberInfoEntity memberInfo = new MemberInfoEntity();
		return memberInfo;
	}

	public static MemberInfoEntity kakaoSignup(String memberName, String email, String profileImage, String ageRange, String gender, String birth) {
		MemberInfoEntity memberInfo = new MemberInfoEntity();
		memberInfo.setMemberName(memberName);
		memberInfo.setEmail(email);
		memberInfo.setProfileImage(profileImage);
		memberInfo.setAgeRange(ageRange);
		memberInfo.setGender(gender);
		memberInfo.setBirth(birth);
		return memberInfo;
	}

	public void updateKaKaoProfile(String memberName, String email, String profileImage, String ageRange, String gender, String birth) {
		setMemberName(memberName);
		setEmail(email);
		setProfileImage(profileImage);
		setAgeRange(ageRange);
		setGender(gender);
		setBirth(birth);
	}

	public void updateName(String memberName) {
		this.memberName = memberName;
	}

	public void updateTel(String tel) {
		this.tel = tel;
	}

	public void updateEmail(String email) {
		this.email = email;
	}

	public void deleteMember() {
		this.deleteDate=new Date(System.currentTimeMillis());
	}

}