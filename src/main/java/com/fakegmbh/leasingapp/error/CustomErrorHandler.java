package com.fakegmbh.leasingapp.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomErrorHandler extends ResponseEntityExceptionHandler {

    private static final String DATABASE_ERROR = "A database exception just happened";
    private static final String GENERAL_ERROR = "An exception just happened";

    @ExceptionHandler(java.sql.SQLException.class)
    public ResponseEntity<ApiError> handleSqlException(final java.sql.SQLException exception,
                                                       final ServletWebRequest webRequest) {
        final ApiError error = ApiError.builder()
                                       .status(HttpStatus.BAD_REQUEST)
                                       .message(exception.getMessage())
                                       .build();
        log.error(DATABASE_ERROR, exception);
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception exception,
                                                             @Nullable final Object body,
                                                             final HttpHeaders headers,
                                                             final HttpStatus status,
                                                             final WebRequest request) {
        final ApiError error = ApiError.builder()
                                       .status(status)
                                       .message(exception.getMessage())
                                       .build();
        log.error(GENERAL_ERROR, exception);
        return new ResponseEntity<>(error, headers, status);
    }
}