package com.vnpt.system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private String status;
    private String message;
    private T data;
    private Long timestamp;
    private String requestId;
    
    public BaseResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
    
    public BaseResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    
    public BaseResponse(String status, String message, T data, String requestId) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.requestId = requestId;
    }
}
