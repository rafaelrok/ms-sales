package br.com.rafaelvieira.pagarmeapi.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author rafae
 */
@ControllerAdvice
public class JsonExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException authenticationException) {
        ExceptionDetails authenticationExceptionDetails = new ExceptionDetails();
        authenticationExceptionDetails.setStatus(HttpStatus.UNAUTHORIZED.value());
        authenticationExceptionDetails.setMessage(authenticationException.getMessage());
        return new ResponseEntity<>(authenticationExceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException validationException) {
        ExceptionDetails validationExceptionDetails = new ExceptionDetails();
        validationExceptionDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        validationExceptionDetails.setMessage(validationException.getMessage());
        return new ResponseEntity<>(validationExceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<?> handleOperacaoProibidaException(OperationException operationException) {
        ExceptionDetails operationExceptionHandlerDetails = new ExceptionDetails();
        operationExceptionHandlerDetails.setStatus(HttpStatus.FORBIDDEN.value());
        operationExceptionHandlerDetails.setMessage(operationException.getMessage());
        return new ResponseEntity<>(operationExceptionHandlerDetails, HttpStatus.FORBIDDEN);
    }
}
