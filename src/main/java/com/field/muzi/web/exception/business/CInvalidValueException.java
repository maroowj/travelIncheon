package com.field.muzi.web.exception.business;

import com.field.muzi.web.common.dto.ErrorCode;

public class CInvalidValueException extends CBusinessException {
    public CInvalidValueException(ErrorCode errorCode) {
            super(errorCode);
    }

    public static class CAlreadySignedupException extends CInvalidValueException {
        public CAlreadySignedupException() {
            super(ErrorCode.ALREADY_SIGNEDUP);
        }
    }

    public static class CLoginFailedException extends CInvalidValueException {
        public CLoginFailedException() {
            super(ErrorCode.LOGIN_FAIL);
        }
    }

}
