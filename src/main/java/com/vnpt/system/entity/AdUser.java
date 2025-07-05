package com.vnpt.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "ad_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUser {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_xoa")
    private String nguoiXoa;

    @Column(name = "password")
    private String password;

    @Column(name = "thoi_gian_cap_nhat")
    private LocalDateTime thoiGianCapNhat;

    @Column(name = "thoi_gian_tao")
    private LocalDateTime thoiGianTao;

    @Column(name = "thoi_gian_xoa")
    private LocalDateTime thoiGianXoa;

    @Column(name = "username")
    private String username;

}
