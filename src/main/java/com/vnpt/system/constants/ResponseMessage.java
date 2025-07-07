package com.vnpt.system.constants;

public class ResponseMessage {
    
    // Message keys for i18n
    public static final String SUCCESS = "response.success";
    public static final String USER_CREATED = "response.user.created";
    public static final String USER_UPDATED = "response.user.updated";
    public static final String USER_DELETED = "response.user.deleted";
    public static final String LOGIN_SUCCESS = "response.login.success";
    public static final String LOGOUT_SUCCESS = "response.logout.success";
    public static final String TOKEN_VALID = "response.token.valid";
    
    // Error message keys
    public static final String INTERNAL_ERROR = "response.error.internal";
    public static final String VALIDATION_FAILED = "response.error.validation";
    public static final String AUTHENTICATION_FAILED = "response.error.authentication";
    public static final String AUTHORIZATION_FAILED = "response.error.authorization";
    public static final String RESOURCE_NOT_FOUND = "response.error.not_found";
    public static final String CONFLICT_ERROR = "response.error.conflict";
    public static final String BAD_REQUEST_ERROR = "response.error.bad_request";
    public static final String TIMEOUT_ERROR = "response.error.timeout";
    
    // User specific message keys
    public static final String USER_NOT_FOUND = "response.user.not_found";
    public static final String USER_ALREADY_EXISTS = "response.user.already_exists";
    public static final String INVALID_CREDENTIALS = "response.user.invalid_credentials";
    public static final String USERNAME_REQUIRED = "response.user.username_required";
    public static final String PASSWORD_REQUIRED = "response.user.password_required";
    public static final String TOKEN_EXPIRED = "response.user.token_expired";
    public static final String TOKEN_INVALID = "response.user.token_invalid";
    public static final String SESSION_EXPIRED = "response.user.session_expired";
    public static final String INSUFFICIENT_PERMISSIONS = "response.user.insufficient_permissions";
    
    private ResponseMessage() {
        // Private constructor to prevent instantiation
    }
}
