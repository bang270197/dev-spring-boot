package com.devteria.springboot.configuration;

import com.devteria.springboot.entity.Role;
import com.devteria.springboot.security.JwtProperties;
import com.devteria.springboot.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtProperties jwtProperties;

    private static final String[] AUTH_WHITELIST =
            {       "/api/v1/auth/introspect",
                    "/api/v1/auth/login"
            };

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,
                                AUTH_WHITELIST).permitAll()
//                        .requestMatchers(HttpMethod.GET,
//                                "/api/v1/users")
//                        .hasAuthority("ROLE_ADMIN")
//                        .hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer
                                -> jwtConfigurer.decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        ))
        ;

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
// 1. Chuyển đổi chuỗi secret thành SecretKey chuẩn cho thuật toán HMAC
        SecretKey originalKey =
                new SecretKeySpec(jwtProperties.getSecret().getBytes(),
                        "HS256");

        // 2. Sử dụng NimbusJwtDecoder để xây dựng bộ giải mã với thuật toán HS256
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(originalKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        /*
         * (Tùy chọn nâng cao): Nếu bạn muốn thêm các bộ kiểm tra tùy chỉnh (Validators),
         * bạn có thể gắn thêm vào đây, ví dụ: check issuer, audience hoặc custom claim.
         */

        return jwtDecoder;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * Cấu hình bộ chuyển đổi quyền từ JWT sang Spring Security Authorities
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Nếu trong token của bạn, trường chứa quyền tên là "scope" (hoặc "roles"):
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // Xóa bỏ tiền tố "SCOPE_" mặc định của Spring đi
        // grantedAuthoritiesConverter.setAuthoritiesClaimName("scope"); // Tên claim chứa quyền trong JWT của bạn

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
