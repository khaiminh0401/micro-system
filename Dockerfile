# Sử dụng image Redis chính thức
FROM redis:7.2-alpine

# Expose port Redis mặc định
EXPOSE 6379

# Lệnh khởi động Redis server
CMD ["redis-server", "--appendonly", "yes"]