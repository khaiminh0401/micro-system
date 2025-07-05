# Entity Generator cho Spring Boot

## Mô tả
Dự án này có thể tự động generate JPA entities từ PostgreSQL database sử dụng một simple generator.

## Cách sử dụng Entity Generator

### 1. Chạy Entity Generator
```bash
# Windows
gradlew.bat generateEntities

# Linux/Mac
./gradlew generateEntities
```

### 2. Cấu hình Database
Cấu hình database trong `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://your-host:5432/your-database
spring.datasource.username=your-username
spring.datasource.password=your-password
```

### 3. Cấu hình Generator
Sửa file `SimpleEntityGenerator.java` để thay đổi:
- Database connection
- Output directory
- Table filtering

### 4. Các tính năng được generate

#### Entity Class
- `@Entity` và `@Table` annotations
- `@Id` cho primary key
- `@GeneratedValue` cho auto-increment
- `@Column` cho tất cả fields
- Lombok annotations (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`)

#### Repository
- Extends `JpaRepository`
- Custom query methods
- Find by username, password, etc.

#### Service
- CRUD operations
- Business logic
- Transaction management
- Logging

#### Controller
- REST endpoints
- HTTP methods (GET, POST, PUT, DELETE)
- Error handling
- Authentication endpoint

## API Endpoints

### User Management
- `GET /api/users` - Lấy tất cả users
- `GET /api/users/{id}` - Lấy user theo ID
- `GET /api/users/username/{username}` - Lấy user theo username
- `POST /api/users` - Tạo user mới
- `PUT /api/users/{id}` - Cập nhật user
- `DELETE /api/users/{id}` - Xóa user
- `POST /api/users/authenticate` - Xác thực user

### Example Request
```json
# POST /api/users
{
  "username": "john_doe",
  "password": "password123",
  "nguoiTao": "admin"
}

# POST /api/users/authenticate
{
  "username": "john_doe",
  "password": "password123"
}
```

## Customization

### Thêm bảng mới
1. Tạo bảng trong database
2. Chạy `gradlew generateEntities`
3. Tạo Repository, Service, Controller tương ứng

### Sửa đổi Entity Generator
File: `src/main/java/com/vnpt/system/generator/SimpleEntityGenerator.java`

#### Thay đổi database connection:
```java
private static final String DB_URL = "your-database-url";
private static final String DB_USER = "your-username";
private static final String DB_PASSWORD = "your-password";
```

#### Thay đổi output directory:
```java
private static final String OUTPUT_DIR = "src/main/java/com/vnpt/system/entity/";
```

#### Filter tables:
```java
private static List<String> getTables(Connection connection) throws SQLException {
    List<String> tables = new ArrayList<>();
    DatabaseMetaData metaData = connection.getMetaData();
    
    // Chỉ lấy tables có prefix "user_"
    ResultSet rs = metaData.getTables(null, "public", "user_%", new String[]{"TABLE"});
    
    while (rs.next()) {
        String tableName = rs.getString("TABLE_NAME");
        tables.add(tableName);
    }
    
    return tables;
}
```

## Cấu trúc thư mục được generate
```
src/main/java/com/vnpt/system/
├── entity/          # JPA Entities
├── repository/      # Spring Data Repositories
├── service/         # Business Logic Services
├── controller/      # REST Controllers
└── generator/       # Entity Generator
```

## Dependencies được sử dụng
- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Spring Boot Starter Security
- PostgreSQL Driver
- Lombok
- Hibernate Tools

## Troubleshooting

### Lỗi Java version
Ensure JAVA_HOME points to JDK 21:
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-21
gradlew.bat generateEntities

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
./gradlew generateEntities
```

### Lỗi database connection
1. Kiểm tra database server đang chạy
2. Kiểm tra firewall settings
3. Kiểm tra credentials trong application.properties

### Lỗi permissions
Ensure output directory có quyền write:
```bash
chmod 755 src/main/java/com/vnpt/system/entity/
```
