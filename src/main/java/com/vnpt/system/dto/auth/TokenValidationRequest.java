package com.vnpt.system.dto.auth;

import com.vnpt.system.dto.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TokenValidationRequest extends BaseRequest {
    
    @NotBlank(message = "{validation.token.required}")
    private String token;
    
    public TokenValidationRequest(String token) {
        super();
        this.token = token;
    }
}
