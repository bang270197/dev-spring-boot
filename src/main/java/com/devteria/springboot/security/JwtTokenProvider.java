package com.devteria.springboot.security;

import com.devteria.springboot.dto.request.IntrospectRequest;
import com.devteria.springboot.dto.response.IntrospectResponse;
import com.devteria.springboot.service.impl.UserServiceImpl;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private static final Logger log = LogManager.getLogger(JwtTokenProvider.class);
    /**
     * Tạo JWT từ thông tin của User (Username)
     */
    public String generateToken(String username) {
        try {
            // 1. HEADER (Định nghĩa thuật toán ký, mặc định ở đây là HS256)
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT)
                    .build();

            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());
            // 2. PAYLOAD (Chứa claims: subject, thời gian, dữ liệu tùy chỉnh...)
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer("java-springboot")
                    .issueTime(new Date())
//                    .expirationTime(new Date(
//                            Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
//                    ))
                    .expirationTime(expiryDate)
                    .claim("customClaim", "customClaimValue")
                    .build();

            Payload payload = new Payload(claimsSet.toJSONObject());

            // Kết hợp Header và Payload thành SignedJWT chưa ký
            JWSObject signedJWT = new JWSObject(header, payload);

            // 3. SIGNATURE (Tạo chữ ký bằng Secret Key)
            JWSSigner signer = new MACSigner(jwtProperties.getSecret().getBytes());


            signedJWT.sign(signer);

            // Trả về chuỗi JWT hoàn chỉnh (Header.Payload.Signature)
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error("Generating JWT token failed - {}", e.getMessage());
            throw new RuntimeException("Generating JWT token failed", e);
        }

    }

    public boolean verify(String token)  {
        try {
            JWSVerifier verifier = new MACVerifier(jwtProperties.getSecret().getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            if (!signedJWT.verify(verifier)) {
                return false;
            }

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            if (null == expirationTime || expirationTime.before(new Date())) {
                return false;
            }
            return true;
        } catch (ParseException | JOSEException e){
            log.error("Verifying JWT token failed - {}", e.getMessage());
            return false;
        }
    }

}
