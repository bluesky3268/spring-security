package com.hyunbenny.security.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY) // 비어있으면 빼고 응답함
public class ErrorResponse {
    private String code;
    private String message;
    private Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" + "code='" + code + '\'' + ", message='" + message + '\'' + ", validation=" + validation + '}';
    }

}
