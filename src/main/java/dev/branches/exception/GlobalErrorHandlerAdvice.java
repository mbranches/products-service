package dev.branches.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandlerAdvice {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultMessageError> handlerNotFoundException(BadRequestException e) {
        DefaultMessageError error = new DefaultMessageError(e.getStatusCode().value(), e.getReason());

        return ResponseEntity.status(e.getStatusCode()).body(error);
    }
}
