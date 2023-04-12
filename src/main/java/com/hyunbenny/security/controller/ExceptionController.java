package com.hyunbenny.security.controller;

import com.hyunbenny.security.dto.response.ErrorResponse;
import com.hyunbenny.security.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> CustomExceptionHandler(CustomException e) {
        log.error(e.getMessage(), e);

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .build();

        ResponseEntity<ErrorResponse> responseEntity = ResponseEntity.status(e.getStatusCode())
                .body(body);

        return responseEntity;
    }

}
