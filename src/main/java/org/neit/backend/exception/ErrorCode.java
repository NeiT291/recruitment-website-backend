package org.neit.backend.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username is invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password is invalid", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1005, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1006, "User not found", HttpStatus.NOT_FOUND),
    COMPANY_NOT_FOUND(1007, "Company not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1008, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID(1009, "Token is invalid", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(1009, "You do not have permission", HttpStatus.FORBIDDEN),
    DOB_INVALID(1010, "Invalid date of birth", HttpStatus.BAD_REQUEST),
    CANNOT_UPLOAD_IMAGE(1011, "Can't upload image", HttpStatus.BAD_REQUEST),
    JOB_NOT_FOUND(1012, "Job not found", HttpStatus.NOT_FOUND),
    DEADLINE_RECEDED(1013, "Deadline received", HttpStatus.BAD_REQUEST),
    CITY_NOT_FOUND(1014, "City not found", HttpStatus.NOT_FOUND),
    ;
    private int code;
    private String message;
    private HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.statusCode = status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
