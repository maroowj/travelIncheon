package com.field.muzi.domain.entity;


import com.field.muzi.domain.base.BaseTimeEntity;
import com.field.muzi.domain.base.SeqManager;
import com.field.muzi.domain.user.Role;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(value = AccessLevel.PROTECTED)
@Table (name = "member")
@DynamicInsert
@DynamicUpdate
@Entity
@ToString
public class MemberEntity extends BaseTimeEntity implements UserDetails {

	// strategy 수정 후 사용
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_member")
	@GenericGenerator(name = "seq_manager_member", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
			@org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "mber_"),
			@org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
	})
	@Column(nullable = false, updatable = false, length = 15)
	@Id
	private String memberSeq;

	@OneToOne(fetch = EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "memberInfoSeq")
	private MemberInfoEntity memberInfo;

	@Column(nullable = false, unique = true, length = 200)
	private String memberId;

	@Column(nullable = false, length = 200)
	private String password;

	@Column(length = 20)
	private String username;
	
	@Column(nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean withdrawal;

	@ElementCollection(fetch = EAGER)
	@Enumerated(EnumType.STRING)
	private List<Role> roles = new ArrayList<>();

	@Column(length = 30)
	private String snsId;

	private String provider;

	@Builder
	public MemberEntity(String memberId, String memberPwd, boolean withdrawal) {
		this.memberId = memberId;
		this.password = memberPwd;
		this.withdrawal = withdrawal;
	}

	public static MemberEntity create(String memberId, String memberPwd, String provider) {
		MemberEntity member = new MemberEntity();
		member.setMemberId(memberId);
		member.setPassword(memberPwd);
		member.setProvider(provider);
		member.setRoles(Collections.singletonList(Role.USER));
		member.setMemberInfo(MemberInfoEntity.create());
		return member;
	}

	public static MemberEntity kakaoSignup(String memberId, String memberPwd, String provider, String snsId, String memberName,
										   String email, String profileImage, String ageRange, String gender, String birth) {
		MemberEntity member = new MemberEntity();
		member.setSnsId(snsId);
		member.setMemberId(memberId);
		member.setPassword(memberPwd);
		member.setProvider(provider);
		member.setRoles(Collections.singletonList(Role.USER));
		member.setMemberInfo(MemberInfoEntity.kakaoSignup(memberName, email, profileImage, ageRange, gender, birth));
		return member;
	}

	public void dropMember() {
		this.withdrawal = true;
	}

	public void resetPwd(String resetPwd) {
		this.password = resetPwd;
	}

	@Override
	public String getUsername() {
		return this.memberSeq;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles
				.stream().map((role) -> new SimpleGrantedAuthority(role.getValue()))
				.collect(Collectors.toList());
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static MemberEntity createSocial(String id, String password, String provider, String snsId) {
		MemberEntity user = create(id, password, provider);
		user.setSnsId(snsId);
		return user;
	}

}