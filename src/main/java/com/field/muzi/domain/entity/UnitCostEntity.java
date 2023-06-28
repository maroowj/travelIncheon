package com.field.muzi.domain.entity;

import com.field.muzi.domain.base.SeqManager;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table (name = "unitCost")
@DynamicInsert
@DynamicUpdate
@Entity
@ToString
public class UnitCostEntity {
	// strategy 수정 후 사용
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manager_unitCost")
	@GenericGenerator(name = "seq_manager_unitCost", strategy = "com.field.muzi.domain.base.SeqManager", parameters = {
			@org.hibernate.annotations.Parameter(name = SeqManager.INCREMENT_PARAM, value = "1"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_USER_SEQ_PARAMETER, value = "unit_"),
			@org.hibernate.annotations.Parameter(name = SeqManager.NUMBER_FORMAT_PARAMETER, value = "%010d"),
			@org.hibernate.annotations.Parameter(name = SeqManager.VALUE_COLUMN_PARAM, value = "seq")
	})
	@Column(nullable = false, updatable = false, length = 15)
	@Id
	private String unitCostSeq;

	@Column(nullable = false, length = 200)
	private int busFare;

	@Column(nullable = false, length = 200)
	private int guideFee;

	@Column(nullable = false, length = 100)
	private int insuranceFee;

	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "datetime default now()")
	private Date createDate;

	@Column
	@UpdateTimestamp
	private Date updateDate;

	@Builder
	public void unitCostEntity(int busFare, int guideFee, int insuranceFee) {

		this.busFare = busFare;
		this.guideFee = guideFee;
		this.insuranceFee = insuranceFee;
	}

	public void updateUnitCost(int busFare, int guideFee, int insuranceFee) {
		this.busFare = busFare;
		this.guideFee = guideFee;
		this.insuranceFee = insuranceFee;
	}

}