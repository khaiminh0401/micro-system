package com.vnpt.system.exception;

import com.vnpt.system.constants.ResponseStatus;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String status;
    private final String errorCode;
    private final Object data;
    
    public BusinessException(String message) {
        super(message);
        this.status = ResponseStatus.ERROR;
        this.errorCode = null;
        this.data = null;
    }
    
    public BusinessException(String status, String message) {
        super(message);
        this.status = status;
        this.errorCode = null;
        this.data = null;
    }
    
    public BusinessException(String status, String message, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.data = null;
    }
    
    public BusinessException(String status, String message, String errorCode, Object data) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.data = data;
    }
}
