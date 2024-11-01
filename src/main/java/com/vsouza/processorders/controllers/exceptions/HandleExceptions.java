package com.vsouza.processorders.controllers.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class HandleExceptions extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        Notification notification = new Notification();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            notification.addError(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            notification.addError(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        notification.setMessage("Argumentos incorretos ou insuficientes!");
        notification.setHttpStatus(badRequest);
        notification.setTime(ZonedDateTime.now(ZoneId.of("Z")));
        return handleExceptionInternal(ex, notification, headers, badRequest, request);
    }

    @ExceptionHandler({ResourceNotFoundException.class, ResourceAccessException.class, EntityNotFoundException.class})
    public ResponseEntity<Notification> handleNotFoundException(ResourceNotFoundException ex,
                                                                WebRequest webRequest){

        HttpStatus status = HttpStatus.NOT_FOUND;
        Notification notification = Notification.builder()
                .httpStatus(status)
                .message(ex.getMessage())
                .uri(webRequest.getDescription(false))
                .time(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(notification, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Notification> handleBadRequestException(BadRequestException ex,
                                                                  WebRequest webRequest){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        Notification notification = Notification.builder()
                .httpStatus(status)
                .message(ex.getMessage())
                .uri(webRequest.getDescription(false))
                .time(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(notification, status);
    }



    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatus status,
                                                                    WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        Notification notification = Notification.builder()
                .httpStatus(badRequest)
                .message(ex.getMessage())
                .time(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(notification, badRequest);
    }


    @ExceptionHandler(value = {InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> handleConstraintViolation(InvalidDataAccessApiUsageException ex, HttpHeaders headers,
                                                            HttpStatus status,
                                                            WebRequest request){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        Notification notification = Notification.builder()
                .httpStatus(badRequest)
                .message("Invalid argument")
                .time(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(notification, badRequest);
    }

    @ExceptionHandler(value = {DateTimeParseException.class})
    protected ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatus status,
                                                                    WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        Notification notification = Notification.builder()
                .httpStatus(badRequest)
                .message("Utilize o formato de data yyyyMMdd")
                .time(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(notification, badRequest);
    }
}
