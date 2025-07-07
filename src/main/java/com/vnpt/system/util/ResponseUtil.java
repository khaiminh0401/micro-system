package com.vnpt.system.util;

import com.vnpt.system.constants.ResponseMessage;
import com.vnpt.system.constants.ResponseStatus;
import com.vnpt.system.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {
    
    private static MessageUtil messageUtil;
    
    @Autowired
    public void setMessageUtil(MessageUtil messageUtil) {
        ResponseUtil.messageUtil = messageUtil;
    }
    
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ResponseStatus.SUCCESS, messageUtil.getMessage(ResponseMessage.SUCCESS), data);
    }
    
    public static <T> BaseResponse<T> success(String messageKey, T data) {
        return new BaseResponse<>(ResponseStatus.SUCCESS, messageUtil.getMessage(messageKey), data);
    }
    
    public static <T> BaseResponse<T> created(T data) {
        return new BaseResponse<>(ResponseStatus.CREATED, messageUtil.getMessage(ResponseMessage.USER_CREATED), data);
    }
    
    public static <T> BaseResponse<T> created(String messageKey, T data) {
        return new BaseResponse<>(ResponseStatus.CREATED, messageUtil.getMessage(messageKey), data);
    }
    
    public static <T> BaseResponse<T> error(String messageKey) {
        return new BaseResponse<>(ResponseStatus.ERROR, messageUtil.getMessage(messageKey));
    }
    
    public static <T> BaseResponse<T> error(String status, String messageKey) {
        return new BaseResponse<>(status, messageUtil.getMessage(messageKey));
    }
    
    public static <T> BaseResponse<T> validationError(String messageKey) {
        return new BaseResponse<>(ResponseStatus.VALIDATION_ERROR, messageUtil.getMessage(messageKey));
    }
    
    public static <T> BaseResponse<T> notFound(String messageKey) {
        return new BaseResponse<>(ResponseStatus.NOT_FOUND, messageUtil.getMessage(messageKey));
    }
    
    public static <T> BaseResponse<T> unauthorized(String messageKey) {
        return new BaseResponse<>(ResponseStatus.AUTHENTICATION_ERROR, messageUtil.getMessage(messageKey));
    }
    
    public static <T> BaseResponse<T> conflict(String messageKey) {
        return new BaseResponse<>(ResponseStatus.CONFLICT, messageUtil.getMessage(messageKey));
    }
    
    public static <T> BaseResponse<T> loginSuccess(T data) {
        return new BaseResponse<>(ResponseStatus.SUCCESS, messageUtil.getMessage(ResponseMessage.LOGIN_SUCCESS), data);
    }
    
    public static <T> BaseResponse<T> invalidCredentials() {
        return new BaseResponse<>(ResponseStatus.INVALID_CREDENTIALS, messageUtil.getMessage(ResponseMessage.INVALID_CREDENTIALS));
    }
    
    public static <T> BaseResponse<T> userAlreadyExists() {
        return new BaseResponse<>(ResponseStatus.USER_ALREADY_EXISTS, messageUtil.getMessage(ResponseMessage.USER_ALREADY_EXISTS));
    }
    
    public static <T> BaseResponse<T> userNotFound() {
        return new BaseResponse<>(ResponseStatus.USER_NOT_FOUND, messageUtil.getMessage(ResponseMessage.USER_NOT_FOUND));
    }
    
    public static <T> BaseResponse<T> tokenValid(T data) {
        return new BaseResponse<>(ResponseStatus.SUCCESS, messageUtil.getMessage(ResponseMessage.TOKEN_VALID), data);
    }
    
    public static <T> BaseResponse<T> tokenInvalid() {
        return new BaseResponse<>(ResponseStatus.TOKEN_INVALID, messageUtil.getMessage(ResponseMessage.TOKEN_INVALID));
    }
}
