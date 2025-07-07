package com.vnpt.system.controller;

import com.vnpt.system.jwt.JwtUtils;
import com.vnpt.system.service.AdUserService;
import com.vnpt.system.dto.auth.LoginRequest;
import com.vnpt.system.dto.auth.LoginResponse;
import com.vnpt.system.dto.auth.TokenValidationRequest;
import com.vnpt.system.dto.auth.TokenValidationResponse;
import com.vnpt.system.constants.ResponseStatus;
import com.vnpt.system.constants.ResponseMessage;
import com.vnpt.system.util.ResponseUtil;
import com.vnpt.system.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdUserService adUserService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Lưu thông tin đăng nhập vào Redis
            cacheUserSession(loginRequest.getUsername(), jwt, userDetails);

            // Tạo response data
            LoginResponse.LoginData loginData = new LoginResponse.LoginData(
                jwt,
                "Bearer",
                userDetails.getUsername(),
                userDetails.getAuthorities(),
                System.currentTimeMillis() + 86400000L // 24 hours
            );

            return ResponseEntity.ok(ResponseUtil.loginSuccess(loginData));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(ResponseUtil.invalidCredentials());
        } catch (BusinessException e) {
            throw e; // Let GlobalExceptionHandler handle it
        } catch (Exception e) {
            throw new BusinessException(ResponseStatus.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_ERROR);
        }
    }

    /**
     * Lưu thông tin phiên đăng nhập vào Redis
     */
    private void cacheUserSession(String username, String jwt, UserDetails userDetails) {
        try {
            // Lưu thông tin user login vào Redis với key: "session:username"
            Map<String, Object> sessionData = new HashMap<>();
            sessionData.put("username", username);
            sessionData.put("token", jwt);
            sessionData.put("authorities", userDetails.getAuthorities());
            sessionData.put("loginTime", System.currentTimeMillis());

            String sessionKey = "session:" + username;
            redisTemplate.opsForValue().set(sessionKey, sessionData);

            // Lưu token mapping với username: "token:jwt" -> username
            String tokenKey = "token:" + jwt;
            redisTemplate.opsForValue().set(tokenKey, username);

            // Lưu thông tin user đã login vào cache bằng AdUserService
            adUserService.authenticateAndCache(username, ""); // Password không cần thiết cho cache

            System.out.println("Cached user session in Redis: " + sessionKey);
        } catch (Exception e) {
            System.err.println("Error caching user session: " + e.getMessage());
        }
    }

    /**
     * Lấy thông tin phiên đăng nhập từ Redis
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getUserSession(String username) {
        try {
            String sessionKey = "session:" + username;
            Object sessionData = redisTemplate.opsForValue().get(sessionKey);
            if (sessionData instanceof Map) {
                return (Map<String, Object>) sessionData;
            }
        } catch (Exception e) {
            System.err.println("Error getting user session: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lấy username từ token trong Redis
     */
    public String getUsernameFromToken(String token) {
        try {
            String tokenKey = "token:" + token;
            Object username = redisTemplate.opsForValue().get(tokenKey);
            if (username instanceof String) {
                return (String) username;
            }
        } catch (Exception e) {
            System.err.println("Error getting username from token: " + e.getMessage());
        }
        return null;
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@Valid @RequestBody TokenValidationRequest request) {
        try {
            boolean isValid = jwtUtils.validateJwtToken(request.getToken());
            
            if (isValid) {
                String username = jwtUtils.getUserNameFromJwtToken(request.getToken());
                TokenValidationResponse.TokenData tokenData = new TokenValidationResponse.TokenData(
                    true,
                    username,
                    System.currentTimeMillis() + 86400000L, // 24 hours
                    "session:" + username
                );
                return ResponseEntity.ok(ResponseUtil.tokenValid(tokenData));
            } else {
                return ResponseEntity.status(401).body(ResponseUtil.tokenInvalid());
            }
        } catch (BusinessException e) {
            throw e; // Let GlobalExceptionHandler handle it
        } catch (Exception e) {
            throw new BusinessException(ResponseStatus.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_ERROR);
        }
    }
}
