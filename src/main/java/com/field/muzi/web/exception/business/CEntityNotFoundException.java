package com.field.muzi.web.exception.business;


import com.field.muzi.web.common.dto.ErrorCode;

public class CEntityNotFoundException extends CBusinessException {
    public CEntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class CUserNotFoundException extends CEntityNotFoundException {
        public CUserNotFoundException() {
            super(ErrorCode.USER_NOT_FOUND);
        }
    }

    public static class CDiyNotFoundException extends CEntityNotFoundException {
        public CDiyNotFoundException() {
            super(ErrorCode.DIY_NOT_FOUND);
        }
    }

    public static class CDiyDetailsNotFoundException extends CEntityNotFoundException {
        public CDiyDetailsNotFoundException() {
            super(ErrorCode.DIY_DETAILS_NOT_FOUND);
        }
    }

    public static class CDiyOptionsNotFoundException extends CEntityNotFoundException {
        public CDiyOptionsNotFoundException() {
            super(ErrorCode.DIY_OPTIONS_NOT_FOUND);
        }
    }

    public static class CFormNotFoundException extends CEntityNotFoundException {
        public CFormNotFoundException() {
            super(ErrorCode.FORM_NOT_FOUND);
        }
    }

    public static class CInquiryNotFoundException extends CEntityNotFoundException {
        public CInquiryNotFoundException() {
            super(ErrorCode.INQUIRY_NOT_FOUND);
        }
    }

    public static class CVrNotFoundException extends CEntityNotFoundException {
        public CVrNotFoundException() {
            super(ErrorCode.VR_NOT_FOUND);
        }
    }
}
