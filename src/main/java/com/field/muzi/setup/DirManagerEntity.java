package com.field.muzi.setup;

import com.field.muzi.domain.base.SeqManager;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table (name = "dirManager")
@DynamicInsert
@Entity
public class DirManagerEntity {

	// strategy 수정 후 사용
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_dir_manager")
	@GenericGenerator(name = "seq_manager_dir_manager", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
			@org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "dire_"),
			@org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
	})
	@Column(nullable = false, updatable = false, length = 15)
	@Id
	private String dirSeq;

	@Column(length = 15)
	private String dirDiv;

	@Column(length = 15)
	private String dirName;

	@Column(nullable = false, length = 200)
	private String dirUrl;

	@Column(length = 200)
	private String dirDesc;

	@Builder
	public DirManagerEntity(String dirDiv, String dirName, String dirUrl) {
		this.dirDiv = dirDiv;
		this.dirName = dirName;
		this.dirUrl = dirUrl;
	}
}