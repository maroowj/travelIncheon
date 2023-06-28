package com.field.muzi.web.exception.business;


import com.field.muzi.web.common.dto.ErrorCode;
import lombok.Getter;

@Getter
public class CBusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public CBusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
