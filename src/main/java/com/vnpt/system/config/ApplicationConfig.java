package com.vnpt.system.config;

import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

import java.util.Locale;

@Configuration
public class ApplicationConfig {
    
    @PostConstruct
    public void init() {
        // Đặt locale mặc định cho toàn bộ ứng dụng
        Locale.setDefault(Locale.forLanguageTag("vi"));
        System.setProperty("user.language", "vi");
        System.setProperty("user.country", "VN");
    }
}
