package dev.branches.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.stream.Collectors;

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
        DefaultMessageError error = new DefaultMessageError(HttpStatus.UNAUTHORIZED.value(), "Login or password invalid");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArgumentNotValidMessageError> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String defaultMessage = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.joining(", "));

        ArgumentNotValidMessageError response = new ArgumentNotValidMessageError (HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), defaultMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
