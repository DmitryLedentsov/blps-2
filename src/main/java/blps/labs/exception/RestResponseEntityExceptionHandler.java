package blps.labs.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class, EmptyResultDataAccessException.class, ConstraintViolationException.class})
    protected ResponseEntity<Object> handleDataNotFound(
            RuntimeException e, WebRequest request) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUsernameNotFound(
            RuntimeException e, WebRequest request) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleServer(
            RuntimeException e, WebRequest request) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return handleExceptionInternal(e, "Unknown error occurred", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
