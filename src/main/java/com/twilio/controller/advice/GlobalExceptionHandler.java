package com.twilio.controller.advice;

import com.twilio.exceptions.IllegalArgumentException;
import com.twilio.models.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, ApiErrorResponse>> illegalArgumentException(IllegalArgumentException illegalArgumentException) {

        HashMap<String, ApiErrorResponse> errorResponseHashMap = new HashMap<>();
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now()).category("API_ERROR").message(illegalArgumentException.getReason()).status(HttpStatus.BAD_REQUEST)
                .build();
        errorResponseHashMap.put("errors", apiErrorResponse);
        log.warn(apiErrorResponse.toString());
        return new ResponseEntity<>(errorResponseHashMap, illegalArgumentException.getStatusCode());
    }
}
