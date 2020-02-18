package com.gunaas.rest.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.text.ParseException;

public interface JwtService {
    @NotNull
    String generateLoginToken(@NotNull Long var1) throws JOSEException;

    boolean validateLoginToken(@NotNull String var1) throws BadJOSEException, ParseException, JOSEException;

    @Nullable
    Long getUserIdFromLoginToken(@NotNull String var1) throws BadJOSEException, ParseException, JOSEException;
}
