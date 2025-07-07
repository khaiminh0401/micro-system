package com.vnpt.system.controller;

import com.vnpt.system.entity.AdUser;
import com.vnpt.system.service.AdUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class AdUserController {
    
    private final AdUserService adUserService;
    
    /**
     * Lấy tất cả users
     */
    @GetMapping
    public ResponseEntity<List<AdUser>> getAllUsers() {
        List<AdUser> users = adUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    /**
     * Lấy user theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdUser> getUserById(@PathVariable String id) {
        Optional<AdUser> user = adUserService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Lấy user theo username
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<AdUser> getUserByUsername(@PathVariable String username) {
        Optional<AdUser> user = adUserService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Tạo user mới
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody AdUser user) {
        try {
            // Validate required fields
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                log.error("Username is required");
                return ResponseEntity.badRequest().body("Username is required");
            }
            
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                log.error("Password is required");
                return ResponseEntity.badRequest().body("Password is required");
            }
            
            // Kiểm tra username đã tồn tại
            if (adUserService.existsByUsername(user.getUsername())) {
                log.error("Username already exists: {}", user.getUsername());
                return ResponseEntity.badRequest().body("Username already exists");
            }
            
            log.info("Creating user with username: {}", user.getUsername());
            AdUser createdUser = adUserService.createUser(user);
            log.info("User created successfully: {}", createdUser.getUsername());
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error creating user: " + e.getMessage());
        }
    }
    
    /**
     * Cập nhật user
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdUser> updateUser(@PathVariable String id, @RequestBody AdUser userDetails) {
        try {
            AdUser updatedUser = adUserService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating user", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Xóa user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            adUserService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting user", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Xác thực user
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AdUser> authenticate(@RequestBody AuthRequest authRequest) {
        AdUser user = adUserService.authenticateAndCache(authRequest.getUsername(), authRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    
    /**
     * DTO cho authentication request
     */
    public static class AuthRequest {
        private String username;
        private String password;
        
        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
