package com.vnpt.system.constants;

public class ResponseStatus {
    
    // Success statuses
    public static final String SUCCESS = "SUCCESS";
    public static final String CREATED = "CREATED";
    public static final String UPDATED = "UPDATED";
    public static final String DELETED = "DELETED";
    
    // Error statuses
    public static final String ERROR = "ERROR";
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String AUTHENTICATION_ERROR = "AUTHENTICATION_ERROR";
    public static final String AUTHORIZATION_ERROR = "AUTHORIZATION_ERROR";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String CONFLICT = "CONFLICT";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String TIMEOUT = "TIMEOUT";
    
    // Custom business statuses
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
    public static final String TOKEN_INVALID = "TOKEN_INVALID";
    public static final String SESSION_EXPIRED = "SESSION_EXPIRED";
    public static final String INSUFFICIENT_PERMISSIONS = "INSUFFICIENT_PERMISSIONS";
    
    private ResponseStatus() {
        // Private constructor to prevent instantiation
    }
}
