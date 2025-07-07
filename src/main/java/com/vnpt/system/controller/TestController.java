package com.vnpt.system.controller;

import com.vnpt.system.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @Autowired
    private MessageUtil messageUtil;
    
    @GetMapping("/locale")
    public Map<String, Object> testLocale(@RequestParam(value = "lang", required = false) String language) {
        Map<String, Object> result = new HashMap<>();
        
        // Current locale info
        Locale currentLocale = Locale.getDefault();
        result.put("default_locale", currentLocale.toString());
        result.put("default_language", currentLocale.getLanguage());
        result.put("default_country", currentLocale.getCountry());
        
        // Test messages
        result.put("success_message", messageUtil.getMessage("response.success"));
        result.put("login_success", messageUtil.getMessage("response.login.success"));
        result.put("user_created", messageUtil.getMessage("response.user.created"));
        result.put("invalid_credentials", messageUtil.getMessage("response.user.invalid_credentials"));
        result.put("validation_username_required", messageUtil.getMessage("validation.username.required"));
        result.put("validation_password_required", messageUtil.getMessage("validation.password.required"));
        result.put("error_internal", messageUtil.getMessage("response.error.internal"));
        result.put("error_not_found", messageUtil.getMessage("response.error.not_found"));
        
        return result;
    }
    
    @GetMapping("/locale/with-param")
    public Map<String, String> testLocaleWithParam() {
        Map<String, String> result = new HashMap<>();
        result.put("username_size", messageUtil.getMessage("validation.username.size", 3, 50));
        result.put("password_size", messageUtil.getMessage("validation.password.size", 6, 100));
        return result;
    }
}
