package com.vnpt.system.service;

import com.vnpt.system.entity.AdUser;
import com.vnpt.system.repository.AdUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
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
     * Cache user theo username vào Redis khi truy vấn
     */
    @Cacheable(value = "userCache", key = "#username")
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
        
        // Hash password before saving
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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
                // Hash password before updating
                user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
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
     * Lưu user vào Redis khi login thành công (distributed cache)
     */
    public void cacheUserLogin(AdUser user) {
        if (user != null && user.getUsername() != null) {
            String key = "user:" + user.getUsername();
            redisTemplate.opsForValue().set(key, user);
            log.info("Cached user login in Redis: {}", key);
        }
    }

    /**
     * Lấy user từ Redis theo username (distributed cache)
     */
    @Cacheable(value = "userLoginCache", key = "#username")
    public AdUser getCachedUser(String username) {
        // Trả về null để Spring Cache tự lấy từ Redis
        return null;
    }

    /**
     * Xác thực user
     * Khi login thành công, lưu user vào Redis bằng @CachePut
     */
    @CachePut(value = "userLoginCache", key = "#username")
    public AdUser authenticateAndCache(String username, String password) {
        Optional<AdUser> userOpt = adUserRepository.findByUsernameAndPassword(username, password);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        return null;
    }
}
