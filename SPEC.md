# Project Specification: Product Inventory Management API

## Overview
A RESTful product inventory management API with JWT authentication and role-based access control.

## Tech Stack
- **Language:** Java 25 (Amazon Corretto 25)
- **Framework:** Spring Boot 4.1.2 (Spring Framework 7, Spring Security 7.1)
- **Database:** PostgreSQL (Amazon RDS in production)
- **ORM:** Spring Data JPA / Hibernate
- **Auth:** JWT (jjwt 0.12.6)
- **Build:** Maven
- **Deployment:** EC2 (Ubuntu 22.04) + systemd

## pom.xml
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.1.2</version>
    <relativePath />
</parent>

<properties>
    <java.version>25</java.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.6</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.6</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.6</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>   


# Project Structure
src/main/java/com/example/inventory/
├── InventoryApplication.java
├── config/
│   ├── SecurityConfig.java
│   └── OpenApiConfig.java
├── controller/
│   ├── AuthController.java
│   ├── ProductController.java
│   └── AdminController.java
├── dto/
│   ├── request/
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── ProductCreateRequest.java
│   │   └── ProductUpdateRequest.java
│   └── response/
│       ├── AuthResponse.java
│       ├── ProductResponse.java
│       └── StatsResponse.java
├── entity/
│   ├── Product.java
│   └── User.java
├── repository/
│   ├── ProductRepository.java
│   └── UserRepository.java
├── service/
│   ├── AuthService.java
│   ├── ProductService.java
│   └── AdminService.java
├── security/
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
└── exception/
    ├── GlobalExceptionHandler.java
    └── ResourceNotFoundException.java   


--- 

Here's a spec document you can drop into your project root as `SPEC.md` (or `CLAUDE.md` — Claude Code reads that file automatically for project context):

```markdown
# Project Specification: Product Inventory Management API

## Overview
A RESTful product inventory management API with JWT authentication and role-based access control.

## Tech Stack
- **Language:** Java 25 (Amazon Corretto 25)
- **Framework:** Spring Boot 4.1.2 (Spring Framework 7, Spring Security 7.1)
- **Database:** PostgreSQL (Amazon RDS in production)
- **ORM:** Spring Data JPA / Hibernate
- **Auth:** JWT (jjwt 0.12.6)
- **Build:** Maven
- **Deployment:** EC2 (Ubuntu 22.04) + systemd

## pom.xml
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.1.2</version>
    <relativePath />
</parent>

<properties>
    <java.version>25</java.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.6</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.6</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.6</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Project Structure
```
src/main/java/com/example/inventory/
├── InventoryApplication.java
├── config/
│   ├── SecurityConfig.java
│   └── OpenApiConfig.java
├── controller/
│   ├── AuthController.java
│   ├── ProductController.java
│   └── AdminController.java
├── dto/
│   ├── request/
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── ProductCreateRequest.java
│   │   └── ProductUpdateRequest.java
│   └── response/
│       ├── AuthResponse.java
│       ├── ProductResponse.java
│       └── StatsResponse.java
├── entity/
│   ├── Product.java
│   └── User.java
├── repository/
│   ├── ProductRepository.java
│   └── UserRepository.java
├── service/
│   ├── AuthService.java
│   ├── ProductService.java
│   └── AdminService.java
├── security/
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
└── exception/
    ├── GlobalExceptionHandler.java
    └── ResourceNotFoundException.java
```

## Entities

### User
| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | PK, auto-generated |
| username | String | unique, not null |
| password | String | not null (BCrypt hashed) |
| role | String | not null, default "USER" |
| createdAt | Instant | not null |

### Product
| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | PK, auto-generated |
| name | String | not null, max 200 |
| description | String | nullable, max 2000 |
| price | BigDecimal | not null, precision 10 scale 2 |
| stock | Integer | not null, default 0 |
| imageUrl | String | nullable |
| createdAt | Instant | not null |
| updatedAt | Instant | not null |

## API Endpoints

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | /api/auth/register | Public | Register new user |
| POST | /api/auth/login | Public | Login, returns JWT |
| GET | /api/products | USER+ | List products (paginated: ?page=&size=&sort=) |
| GET | /api/products/{id} | USER+ | Get single product |
| POST | /api/products | USER+ | Create product |
| PUT | /api/products/{id} | USER+ | Update product |
| DELETE | /api/products/{id} | USER+ | Delete product |
| GET | /api/admin/stats | ADMIN | Aggregate stats (total products, total stock, avg price) |

## Security Config
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

## Configuration

### src/main/resources/application.yml (base)
```yaml
spring:
  application:
    name: inventory-api
  jpa:
    hibernate:
      ddl-auto: update   # dev only
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
  datasource:
    url: jdbc:postgresql://localhost:5432/inventory
    username: postgres
    password: postgres

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000
```

### src/main/resources/application-prod.yml
```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

## Deployment (EC2)

### systemd unit: /etc/systemd/system/app.service
```ini
[Unit]
Description=Product Inventory API
After=network.target

[Service]
User=ec2-user
ExecStart=/usr/bin/java -Xms512m -Xmx1024m -jar /opt/app/app.jar
Restart=always
RestartSec=5
Environment=SPRING_PROFILES_ACTIVE=prod
Environment=SPRING_DATASOURCE_URL=jdbc:postgresql://RDS_ENDPOINT:5432/inventory
Environment=SPRING_DATASOURCE_USERNAME=USERNAME
Environment=SPRING_DATASOURCE_PASSWORD=PASSWORD
Environment=JWT_SECRET=CHANGE_ME

[Install]
WantedBy=multi-user.target
```

### Build & Deploy Steps
1. `mvn clean package -DskipTests`
2. `scp target/*.jar ec2-user@EC2_IP:/opt/app/app.jar`
3. `sudo systemctl restart app && sudo systemctl status app`

## Testing
- Use `@SpringBootTest` with `@AutoConfigureMockMvc`
- Use `@MockitoBean` (NOT `@MockBean`) for mocking
- JUnit 6 (Jupiter only)

## Important Notes
- Spring Boot 4.x uses `spring-boot-starter-webmvc` (NOT `spring-boot-starter-web`)
- Jackson 3.x package is `tools.jackson` (NOT `com.fasterxml.jackson`)
- Spring Security 7: CSRF is enabled by default — must explicitly disable for stateless APIs
- Use `@MockitoBean` not `@MockBean` in tests
- MockMvc requires `@AutoConfigureMockMvc` annotation in Boot 4.x
```

**How to use it:**

1. Save as `CLAUDE.md` in your project root — Claude Code picks it up automatically as project context on every conversation.
2. Or save as `SPEC.md` and reference it in your prompt: *"Read SPEC.md and implement the project."*

The document is structured so Claude Code can work through it top-down: stack → dependencies → structure → entities → endpoints → security → config → deployment.

