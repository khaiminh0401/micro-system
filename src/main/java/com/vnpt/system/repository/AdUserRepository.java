package com.vnpt.system.repository;

import com.vnpt.system.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdUserRepository extends JpaRepository<AdUser, String> {
    
    /**
     * Tìm user theo username
     */
    Optional<AdUser> findByUsername(String username);
    
    /**
     * Kiểm tra username có tồn tại không
     */
    boolean existsByUsername(String username);
    
    /**
     * Tìm user theo username và password
     */
    Optional<AdUser> findByUsernameAndPassword(String username, String password);
    
    /**
     * Tìm user theo người tạo
     */
    @Query("SELECT u FROM AdUser u WHERE u.nguoiTao = :nguoiTao")
    Optional<AdUser> findByNguoiTao(@Param("nguoiTao") String nguoiTao);
}
