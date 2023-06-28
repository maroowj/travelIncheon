package com.field.muzi.web.common.dto.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppCompleteTelRequest {

	private String surveySeq;
	private boolean callYn;
	private int callTime;
}