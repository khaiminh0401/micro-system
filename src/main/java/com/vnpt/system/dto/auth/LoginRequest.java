package com.vnpt.system.dto.auth;

import com.vnpt.system.dto.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LoginRequest extends BaseRequest {
    
    @NotBlank(message = "{validation.username.required}")
    @Size(min = 3, max = 50, message = "{validation.username.size}")
    private String username;
    
    @NotBlank(message = "{validation.password.required}")
    @Size(min = 6, max = 100, message = "{validation.password.size}")
    private String password;
    
    public LoginRequest(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
}
