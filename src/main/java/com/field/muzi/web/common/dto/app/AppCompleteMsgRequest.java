package com.field.muzi.web.common.dto.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppCompleteMsgRequest {

	private String surveySeq;
	private String smsSeq;
	private boolean sendSmsYn;

	private String temporary;
	/*private String type;
	private String title;
	private String contents;
	private int smsByte;
	private MultipartFile file;*/
}