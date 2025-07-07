package com.vnpt.system.exception;

import com.vnpt.system.constants.ResponseMessage;
import com.vnpt.system.constants.ResponseStatus;
import com.vnpt.system.dto.BaseResponse;
import com.vnpt.system.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @Autowired
    private MessageUtil messageUtil;
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Object>> handleBusinessException(BusinessException ex) {
        log.error("Business exception: {}", ex.getMessage(), ex);
        
        BaseResponse<Object> response = new BaseResponse<>(
            ex.getStatus(), 
            ex.getMessage(), 
            ex.getData()
        );
        
        HttpStatus httpStatus = mapStatusToHttpStatus(ex.getStatus());
        return ResponseEntity.status(httpStatus).body(response);
    }
    
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<BaseResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication exception: {}", ex.getMessage(), ex);
        
        BaseResponse<Object> response = new BaseResponse<>(
            ResponseStatus.AUTHENTICATION_ERROR,
            messageUtil.getMessage(ResponseMessage.AUTHENTICATION_FAILED)
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument exception: {}", ex.getMessage(), ex);
        
        BaseResponse<Object> response = new BaseResponse<>(
            ResponseStatus.BAD_REQUEST,
            ex.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation exception: {}", ex.getMessage(), ex);
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        BaseResponse<Object> response = new BaseResponse<>(
            ResponseStatus.VALIDATION_ERROR,
            messageUtil.getMessage(ResponseMessage.VALIDATION_FAILED),
            errors
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<Object>> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime exception: {}", ex.getMessage(), ex);
        
        BaseResponse<Object> response = new BaseResponse<>(
            ResponseStatus.INTERNAL_SERVER_ERROR,
            messageUtil.getMessage(ResponseMessage.INTERNAL_ERROR)
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGenericException(Exception ex) {
        log.error("Generic exception: {}", ex.getMessage(), ex);
        
        BaseResponse<Object> response = new BaseResponse<>(
            ResponseStatus.INTERNAL_SERVER_ERROR,
            messageUtil.getMessage(ResponseMessage.INTERNAL_ERROR)
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    private HttpStatus mapStatusToHttpStatus(String status) {
        switch (status) {
            case ResponseStatus.SUCCESS:
            case ResponseStatus.CREATED:
                return HttpStatus.OK;
            case ResponseStatus.BAD_REQUEST:
            case ResponseStatus.VALIDATION_ERROR:
                return HttpStatus.BAD_REQUEST;
            case ResponseStatus.AUTHENTICATION_ERROR:
            case ResponseStatus.INVALID_CREDENTIALS:
                return HttpStatus.UNAUTHORIZED;
            case ResponseStatus.AUTHORIZATION_ERROR:
            case ResponseStatus.INSUFFICIENT_PERMISSIONS:
                return HttpStatus.FORBIDDEN;
            case ResponseStatus.NOT_FOUND:
            case ResponseStatus.USER_NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case ResponseStatus.CONFLICT:
            case ResponseStatus.USER_ALREADY_EXISTS:
                return HttpStatus.CONFLICT;
            case ResponseStatus.TIMEOUT:
                return HttpStatus.REQUEST_TIMEOUT;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
