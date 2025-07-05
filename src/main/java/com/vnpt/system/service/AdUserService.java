package com.vnpt.system.service;

import com.vnpt.system.entity.AdUser;
import com.vnpt.system.repository.AdUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdUserService {
    
    private final AdUserRepository adUserRepository;
    
    /**
     * Lấy tất cả users
     */
    public List<AdUser> getAllUsers() {
        return adUserRepository.findAll();
    }
    
    /**
     * Lấy user theo ID
     */
    public Optional<AdUser> getUserById(String id) {
        return adUserRepository.findById(id);
    }
    
    /**
     * Lấy user theo username
     */
    public Optional<AdUser> getUserByUsername(String username) {
        return adUserRepository.findByUsername(username);
    }
    
    /**
     * Tạo user mới
     */
    public AdUser createUser(AdUser user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        user.setThoiGianTao(LocalDateTime.now());
        
        log.info("Creating new user: {}", user.getUsername());
        return adUserRepository.save(user);
    }
    
    /**
     * Cập nhật user
     */
    public AdUser updateUser(String id, AdUser userDetails) {
        Optional<AdUser> existingUser = adUserRepository.findById(id);
        if (existingUser.isPresent()) {
            AdUser user = existingUser.get();
            
            // Cập nhật thông tin
            if (userDetails.getUsername() != null) {
                user.setUsername(userDetails.getUsername());
            }
            if (userDetails.getPassword() != null) {
                user.setPassword(userDetails.getPassword());
            }
            
            user.setThoiGianCapNhat(LocalDateTime.now());
            user.setNguoiCapNhat(userDetails.getNguoiCapNhat());
            
            log.info("Updating user: {}", user.getUsername());
            return adUserRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + id);
    }
    
    /**
     * Xóa user
     */
    public void deleteUser(String id) {
        Optional<AdUser> user = adUserRepository.findById(id);
        if (user.isPresent()) {
            AdUser existingUser = user.get();
            existingUser.setThoiGianXoa(LocalDateTime.now());
            adUserRepository.save(existingUser);
            log.info("Soft deleted user: {}", existingUser.getUsername());
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    
    /**
     * Kiểm tra username có tồn tại không
     */
    public boolean existsByUsername(String username) {
        return adUserRepository.existsByUsername(username);
    }
    
    /**
     * Xác thực user
     */
    public Optional<AdUser> authenticate(String username, String password) {
        return adUserRepository.findByUsernameAndPassword(username, password);
    }
}
