package com.vnpt.system.test;

import com.vnpt.system.config.I18nConfig;
import com.vnpt.system.config.ApplicationConfig;
import com.vnpt.system.util.MessageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {I18nConfig.class, ApplicationConfig.class, MessageUtil.class})
@ContextConfiguration
public class I18nTest {

    @Autowired
    private MessageUtil messageUtil;

    @Test
    public void testDefaultLocaleIsVietnamese() {
        Locale defaultLocale = Locale.getDefault();
        System.out.println("Default Locale: " + defaultLocale);
        System.out.println("Language: " + defaultLocale.getLanguage());
        System.out.println("Country: " + defaultLocale.getCountry());
        
        // Kiểm tra locale mặc định là tiếng Việt
        assertEquals("vi", defaultLocale.getLanguage());
    }

    @Test
    public void testVietnameseMessages() {
        // Test các message tiếng Việt
        String successMessage = messageUtil.getMessage("response.success");
        String loginSuccess = messageUtil.getMessage("response.login.success");
        String userCreated = messageUtil.getMessage("response.user.created");
        String invalidCredentials = messageUtil.getMessage("response.user.invalid_credentials");
        
        System.out.println("Success Message: " + successMessage);
        System.out.println("Login Success: " + loginSuccess);
        System.out.println("User Created: " + userCreated);
        System.out.println("Invalid Credentials: " + invalidCredentials);
        
        // Kiểm tra message là tiếng Việt
        assertEquals("Thao tác thành công", successMessage);
        assertEquals("Đăng nhập thành công", loginSuccess);
        assertEquals("Tạo người dùng thành công", userCreated);
        assertEquals("Tên người dùng hoặc mật khẩu không hợp lệ", invalidCredentials);
    }

    @Test
    public void testValidationMessages() {
        String usernameRequired = messageUtil.getMessage("validation.username.required");
        String passwordRequired = messageUtil.getMessage("validation.password.required");
        
        System.out.println("Username Required: " + usernameRequired);
        System.out.println("Password Required: " + passwordRequired);
        
        // Kiểm tra validation message là tiếng Việt
        assertEquals("Tên người dùng là bắt buộc", usernameRequired);
        assertEquals("Mật khẩu là bắt buộc", passwordRequired);
    }

    @Test
    public void testParameterizedMessages() {
        String usernameSize = messageUtil.getMessage("validation.username.size", 3, 50);
        String passwordSize = messageUtil.getMessage("validation.password.size", 6, 100);
        
        System.out.println("Username Size: " + usernameSize);
        System.out.println("Password Size: " + passwordSize);
        
        // Kiểm tra parameterized message
        assertEquals("Tên người dùng phải từ 3 đến 50 ký tự", usernameSize);
        assertEquals("Mật khẩu phải từ 6 đến 100 ký tự", passwordSize);
    }
}
