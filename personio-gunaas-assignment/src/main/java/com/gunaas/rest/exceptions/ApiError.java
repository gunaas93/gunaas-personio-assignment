package com.gunaas.rest.exceptions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

public class ApiError {
    @NotNull
    @JsonProperty
    private ErrorCode code;
    @NotNull
    @JsonProperty
    private HttpStatus status;
    @Nullable
    @JsonProperty
    private String message;
    @Nullable
    @JsonProperty
    private List errors;

    @JsonCreator
    public ApiError(@NotNull ErrorCode code, @NotNull HttpStatus status, @Nullable String message, @Nullable String errors) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(errors);
    }

    public ErrorCode getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public List getErrors() {
        return errors;
    }
}
