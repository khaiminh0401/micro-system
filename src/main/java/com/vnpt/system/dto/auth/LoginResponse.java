package com.vnpt.system.dto.auth;

import com.vnpt.system.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LoginResponse extends BaseResponse<LoginResponse.LoginData> {
    
    public LoginResponse(String status, String message, LoginData data) {
        super(status, message, data);
    }
    
    @Data
    @NoArgsConstructor
    public static class LoginData {
        private String token;
        private String type;
        private String username;
        private Collection<?> authorities;
        private Long expiresAt;
        
        public LoginData(String token, String type, String username, Collection<?> authorities, Long expiresAt) {
            this.token = token;
            this.type = type;
            this.username = username;
            this.authorities = authorities;
            this.expiresAt = expiresAt;
        }
    }
}
