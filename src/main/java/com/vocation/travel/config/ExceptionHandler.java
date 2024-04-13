package com.vocation.travel.config;

import com.vocation.travel.model.ApiError;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ExceptionHandler {

    ResponseEntity<ApiError> outputError();

    @AllArgsConstructor
    @Getter
    @Setter
    class SystemErrorException extends RuntimeException implements ExceptionHandler {
        private ApiError apiError;

        public SystemErrorException(String message) {
            apiError = new ApiError();
            apiError.setMessage(message);
            apiError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiError.setError(null);
        }

        public SystemErrorException(String message, String exception) {
            apiError = new ApiError();
            apiError.setMessage(message);
            apiError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiError.setError(exception);
        }

        @Override
        public ResponseEntity<ApiError> outputError() {
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class BadRequestException extends RuntimeException implements ExceptionHandler {
        private ApiError apiError;

        public BadRequestException(String message) {
            apiError = new ApiError();
            apiError.setMessage(message);
            apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
            apiError.setError(null);
        }

        public BadRequestException(String message, String exception) {
            apiError = new ApiError();
            apiError.setMessage(message);
            apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
            apiError.setError(exception);
        }

        @Override
        public ResponseEntity<ApiError> outputError() {
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
    }
}
