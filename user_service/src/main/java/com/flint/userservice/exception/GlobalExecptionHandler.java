package com.flint.userservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExecptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ProblemDetail handleDuplicateEmailException(DuplicateEmailException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Duplicate Email Error");
        problemDetail.setProperty("errorCode", "DUPLICATE_EMAIL");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("errorCode", "VALIDATION_ERROR");
        problemDetail.setProperty("validationErrors", validationErrors);
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentials(
            InvalidCredentialsException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("Invalid Credentials");
        problemDetail.setProperty("errorCode", "INVALID_CREDENTIALS");
        problemDetail.setProperty("message", ex.getMessage());
        return problemDetail;
    }
}
