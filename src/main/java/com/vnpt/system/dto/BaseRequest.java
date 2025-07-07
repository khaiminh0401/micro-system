package com.vnpt.system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest {
    private String requestId;
    private Long timestamp;
    
    public BaseRequest(String requestId) {
        this.requestId = requestId;
        this.timestamp = System.currentTimeMillis();
    }
}
