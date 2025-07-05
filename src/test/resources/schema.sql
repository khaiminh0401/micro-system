-- Test schema for H2 database
CREATE TABLE IF NOT EXISTS ad_user (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nguoi_tao VARCHAR(255),
    thoi_gian_tao TIMESTAMP,
    nguoi_cap_nhat VARCHAR(255),
    thoi_gian_cap_nhat TIMESTAMP,
    nguoi_xoa VARCHAR(255),
    thoi_gian_xoa TIMESTAMP
);
