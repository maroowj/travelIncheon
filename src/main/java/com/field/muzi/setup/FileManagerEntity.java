package com.field.muzi.setup;

import com.field.muzi.domain.base.SeqManager;
import com.field.muzi.domain.listener.FileListener;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table (name = "fileManager")
@DynamicInsert
@EntityListeners(FileListener.class)
@Entity
public class FileManagerEntity {

	// strategy 수정 후 사용
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_file_manager")
	@GenericGenerator(name = "seq_manager_file_manager", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
			@org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "file_"),
			@org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
	})
	@Column(nullable = false, updatable = false, length = 15)
	@Id
	private String fileSeq;

	@ManyToOne
	@JoinColumn(name = "dirSeq")
	private DirManagerEntity dirManager;

	@Column(nullable = false, length = 100)
	private String fileName;

	@Column(nullable = false, length = 100)
	private String originName;

	@Column(length = 10)
	private String fileExtension;

	@Column
	private int fileSize;

	@Column(length = 200)
	private String etc;

	@Column(nullable = false)
	@CreationTimestamp
	private Date date;

	@Builder
	public FileManagerEntity(DirManagerEntity dirManager, String fileName, String originName, String fileExtension,
	                         int fileSize, String etc) {
		this.dirManager = dirManager;
		this.fileName = fileName;
		this.originName = originName;
		this.fileExtension = fileExtension;
		this.fileSize = fileSize;
		this.etc = etc;
	}
}