package com.vnpt.system.dto.auth;

import com.vnpt.system.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TokenValidationResponse extends BaseResponse<TokenValidationResponse.TokenData> {
    
    public TokenValidationResponse(String status, String message, TokenData data) {
        super(status, message, data);
    }
    
    @Data
    @NoArgsConstructor
    public static class TokenData {
        private Boolean valid;
        private String username;
        private Long expiresAt;
        private String sessionId;
        
        public TokenData(Boolean valid, String username, Long expiresAt, String sessionId) {
            this.valid = valid;
            this.username = username;
            this.expiresAt = expiresAt;
            this.sessionId = sessionId;
        }
    }
}
