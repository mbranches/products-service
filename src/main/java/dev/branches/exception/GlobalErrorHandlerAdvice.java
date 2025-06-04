package dev.branches.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandlerAdvice {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultMessageError> handlerBadRequestException(BadRequestException e) {
        DefaultMessageError error = new DefaultMessageError(e.getStatusCode().value(), e.getReason());

        return ResponseEntity.status(e.getStatusCode()).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultMessageError> handlerNotFoundException(NotFoundException e) {
        DefaultMessageError error = new DefaultMessageError(e.getStatusCode().value(), e.getReason());

        return ResponseEntity.status(e.getStatusCode()).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DefaultMessageError> handlerBadCredentialsException(BadCredentialsException e) {
        DefaultMessageError error = new DefaultMessageError(HttpStatus.UNAUTHORIZED.value(), "User or password invalid");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
