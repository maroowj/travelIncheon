package com.field.muzi.web.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /**
     * COMMON
     */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "CMM-001", "잘못된 입력입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "CMM-002", "Method Not Allowed"),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "CMM-003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "CMM-004", "Server Error"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST.value(), "CMM-005", "Invalid Type Value"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "CMM-006", "접근이 거부되었습니다."),
    JSON_WRITE_ERROR(HttpStatus.UNAUTHORIZED.value(), "CMM-007", "JSON content that are not pure I/O problems"),

    /**
     * IO
     */
    FILE_CONVERT_FAILED(HttpStatus.BAD_REQUEST.value(), "IO-001", "파일을 변환할 수 없습니다."),
    INVALID_FILE_FORMAT(HttpStatus.BAD_REQUEST.value(), "IO-002", "잘못된 형식의 파일입니다."),
    CLOUD_COMMUNICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "IO-003", "파일 업로드 중 오류가 발생했습니다."),

    /**
     * BUSINESS
     * OMS-1xxx
     */
    LOGIN_FAIL(HttpStatus.BAD_REQUEST.value(), "OMS-1000", "존재하지 않는 계정이거나, 잘못된 비밀번호입니다."),
    ALREADY_SIGNEDUP(HttpStatus.BAD_REQUEST.value(), "OMS-1001", "이미 가입한 사용자입니다."),
    MEMBER_NOT_AUTHENTICATION(HttpStatus.UNAUTHORIZED.value(), "OMS-1002", "인증된 사용자가 아닙니다."),
    APPINTRO_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1003", "앱 인트로가 존재하지 않습니다."),
    CONTENTLOG_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1004", "컨텐츠 로그가 존재하지 않습니다."),
    ENV_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1005", "환경설정이 존재하지 않습니다."),
    GROUPACCOUNT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1006", "그룹 계정이 존재하지 않습니다."),
    GROUPLOG_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1007", "그룹 로그가 존재하지 않습니다."),
    GROUPPLAN_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1008", "그룹 플랜이 존재하지 않습니다."),
    GROUPSTATE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1009", "그룹 상태가 존재하지 않습니다."),
    KAKAOFRIENDS_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1010", "카카오 친구가 존재하지 않습니다."),
    KAKAOMANAGER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1011", "카카오 매니저가 존재하지 않습니다."),
    KAKAOMSG_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1012", "카카오 메시지가 존재하지 않습니다."),
    KAKAOMSGINFO_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1013", "카카오 메시지 정보가 존재하지 않습니다."),
    MEMBERACTIVITIES_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1014", "사용자 활동이 존재하지 않습니다."),
    MEMBERACTIVITYLOG_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1015", "사용자 활동 로그가 존재하지 않습니다."),
    MEMBERACTIVITYTOTAL_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1016", "사용자 활동 합산이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1017", "사용자가 존재하지 않습니다."),
    MEMBERINFO_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1018", "사용자 정보가 존재하지 않습니다."),
    MEMBERLOG_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1019", "사용자 로그가 존재하지 않습니다."),
    MEMBERTYPE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1020", "사용자 타입이 존재하지 않습니다."),
    NOTICEMSG_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1021", "공지사항 메시지가 존재하지 않습니다."),
    NOTICEMSGINFO_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1022", "공지사항 메시지 정보가 존재하지 않습니다."),
    PLANMANAGER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1023", "플랜 매니저가 존재하지 않습니다."),
    RECEIVEDINFO_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1024", "받은 정보가 존재하지 않습니다."),
    RECOMMENDERCODE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1025", "추천인 코드가 존재하지 않습니다."),
    REFRESHTOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1026", "리프레시 토큰이 존재하지 않습니다."),
    RESERVEDMSG_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1027", "예약 메시지가 존재하지 않습니다."),
    SAVEDMSG_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1028", "저장 메시지가 존재하지 않습니다."),
    USEDAPP_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1029", "사용 앱이 존재하지 않습니다."),
    USERREPORT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1030", "사용자 보고서가 존재하지 않습니다."),
    KAKAOFRIENDSLOG_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1031", "카카오 친구 로그가 존재하지 않습니다."),
    RECOMMENDER_CODE_EXPIRED(HttpStatus.BAD_REQUEST.value(), "OMS-1032", "추천인 코드가 만료되었습니다."),
    FRIEND_INFO_EXPIRED(HttpStatus.BAD_REQUEST.value(), "OMS-1033", "친구정보입력 기간이 만료되었습니다."),
    MEMBER_OWN_NOMINEE_ERROR(HttpStatus.BAD_REQUEST.value(), "OMS-1034", "사용자의 피추천인이 아닙니다."),
    MEMBER_NOT_ACCEPTED(HttpStatus.BAD_REQUEST.value(), "OMS-1035", "승인된 사용자가 아닙니다."),
    OUTSTANDING_ACCEPTANCE_ERROR(HttpStatus.BAD_REQUEST.value(), "OMS-1036", "승인 대기중이 아닙니다."),
    INVALID_MESSAGE_TEMPLATE_TYPE(HttpStatus.BAD_REQUEST.value(), "OMS-1037", "잘못된 메시지 템플릿 타입입니다."),
    MEMBER_REFUSED(HttpStatus.BAD_REQUEST.value(), "OMS-1038", "승인이 거절된 사용자입니다."),
    MEMBER_NOT_REFUSED(HttpStatus.BAD_REQUEST.value(), "OMS-1039", "승인이 거절된 사용자가 아닙니다."),
    RECOMMENDER_CODE_INSUFFICIENT_ERROR(HttpStatus.BAD_REQUEST.value(), "OMS-1040", "추천인 코드가 부족합니다."),
    KAKAOFRIENDSINFCODE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "OMS-1041", "카카오 친구 정보 코드가 존재하지 않습니다."),
    ALREADY_FRIEND_SIGNEDUP(HttpStatus.BAD_REQUEST.value(), "OMS-1042", "이미 가입한 친구입니다."),
    AUTH_CODE_EXPIRED(HttpStatus.BAD_REQUEST.value(), "OMS-1043", "카카오 친구 정보 코드가 만료되었습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "MZS-1044", "사용자가 존재하지 않습니다."),
    DIY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "MZS-1045", "DIY 내역이 존재하지 않습니다."),
    DIY_DETAILS_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "MZS-1046", "DIY 상세 내역이 존재하지 않습니다."),
    DIY_OPTIONS_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "MZS-1047", "DIY 옵션 내역이 존재하지 않습니다."),
    FORM_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "MZS-1048", "문의 양식이 존재하지 않습니다."),
    INQUIRY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "MZS-1049", "VR 제작 의뢰가 존재하지 않습니다."),
    VR_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "MZS-1050", "VR 정보가 존재하지 않습니다."),

    /**
     * SOCIAL
     * OMS-2xxx
     */
    SOCIAL_COMMUNICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "MZS-2000", "소셜 인증 과정 중 오류가 발생했습니다."),
    SOCIAL_AGREEMENT_ERROR(HttpStatus.BAD_REQUEST.value(), "MZS-2001", "필수동의 항목에 대해 동의가 필요합니다."),
    INVALID_SOCIAL_TYPE(HttpStatus.BAD_REQUEST.value(), "MZS-2002", "알 수 없는 소셜 타입입니다."),
    SOCIAL_TOKEN_VALID_FAILED(HttpStatus.UNAUTHORIZED.value(), "MZS-2003", "소셜 액세스 토큰 검증에 실패했습니다."),

    /**
     * SECURITY
     * OMS-3xxx
     */
    ACCESS_TOKEN_ERROR(HttpStatus.UNAUTHORIZED.value(), "OMS-3000", "액세스 토큰이 만료되거나 잘못된 값입니다."),
    REFRESH_TOKEN_ERROR(HttpStatus.UNAUTHORIZED.value(), "OMS-3001", "리프레시 토큰이 만료되거나 잘못된 값입니다."),
    TOKEN_PARSE_ERROR(HttpStatus.UNAUTHORIZED.value(), "OMS-3002", "해석할 수 없는 토큰입니다."),
    SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED.value(), "OMS-3003", "JWT의 생성과 복호화할 때의 비밀키가 서로 다릅니다."),
            ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}