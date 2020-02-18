package com.gunaas.rest.service.impl;

import com.gunaas.rest.service.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Date;

@Service
@Scope("prototype")
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiration-time}")
    private Integer expirationTime;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public static final String USER_KEY = "userKey";

    @NotNull
    public String generateLoginToken(@Nullable Long userId) throws JOSEException {
        JWTClaimsSet claims = new JWTClaimsSet.Builder().claim(USER_KEY, userId)
                .expirationTime(generateExpirationDate()).build();
        Payload payload = new Payload(claims.toJSONObject());
        JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
        DirectEncrypter signer = new DirectEncrypter(generateSharedSecret());
        JWEObject jweObject = new JWEObject(header, payload);
        jweObject.encrypt(signer);
        return jweObject.serialize();
    }

    public boolean validateLoginToken(@Nullable String token) throws BadJOSEException, ParseException, JOSEException {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        Long id = getUserIdFromLoginToken(token);
        return (id != null && !isTokenExpired(token));
    }

    @Nullable
    public Long getUserIdFromLoginToken(@NotNull String token) throws BadJOSEException, ParseException, JOSEException {
        ConfigurableJWTProcessor jwtProcessor = configJWTProcessor();
        JWTClaimsSet claims = jwtProcessor.process(token, null);
        return (Long) claims.getClaim(USER_KEY);
    }

    private final ConfigurableJWTProcessor configJWTProcessor() {
        DefaultJWTProcessor jwtProcessor = new DefaultJWTProcessor();
        ImmutableSecret jweKeySource = new ImmutableSecret(this.generateSharedSecret());
        JWEDecryptionKeySelector jweKeySelector = new JWEDecryptionKeySelector(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);
        jwtProcessor.setJWEKeySelector(jweKeySelector);
        return jwtProcessor;
    }

    private final boolean isTokenExpired(String token) {
        Date expiration;
        try {
            expiration = getExpirationDateFromToken(token);
        } catch (BadJOSEException | ParseException | JOSEException e) {
            e.printStackTrace();
            return false;
        }
        return expiration.before(new Date());
    }

    private final Date getExpirationDateFromToken(String token) throws BadJOSEException, ParseException, JOSEException {
        ConfigurableJWTProcessor jwtProcessor = this.configJWTProcessor();
        JWTClaimsSet claims = jwtProcessor.process(token, null);
        return claims.getExpirationTime();
    }

    private final byte[] generateSharedSecret() {
        return secretKey.getBytes();
    }

    private final Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expirationTime);
    }
}