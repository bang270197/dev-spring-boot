package com.devteria.springboot.configuration;

import com.devteria.springboot.security.JwtProperties;
import com.devteria.springboot.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProperties jwtProperties;

    private static final String[] AUTH_WHITELIST =
            {       "/api/v1/auth/introspect",
                    "/api/v1/auth/login",
            };

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,
                                AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer
                                -> jwtConfigurer.decoder(jwtDecoder())
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
}
