package org.saturn.clinicscheduler.handler;

import org.saturn.clinicscheduler.exception.BusyPassportException;
import org.saturn.clinicscheduler.exception.BusyPhoneNumberException;
import org.saturn.clinicscheduler.exception.DepartmentHasAlreadyExistedException;
import org.saturn.clinicscheduler.exception.ObjectNotFoundException;
import org.saturn.clinicscheduler.exception.ScheduleIsBookedException;
import org.saturn.clinicscheduler.exception.SpecialityAlreadyExistException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${specialityExists}")
    public String SPECIALITY_ALREADY_EXISTS;
    @Value("${bookedSchedule}")
    public String BOOKED_SCHEDULE;
    @Value("${departmentExists}")
    public String DEPARTMENT_EXISTS;
    @Value("${busyPassport}")
    public String BUSY_PASSPORT;
    @Value("${busyNumber}")
    public String PHONE_NUMBER_BUSY;
    @Value("${notFound}")
    private String NOT_FOUND;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    protected ResponseEntity<Object> handleObjectNotFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage() + " " + NOT_FOUND;

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BusyPhoneNumberException.class)
    protected ResponseEntity<Object> handlePatientPhoneNumberBusy(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = PHONE_NUMBER_BUSY;

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(SpecialityAlreadyExistException.class)
    protected ResponseEntity<Object> handleSpecialityAlreadyExist(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = SPECIALITY_ALREADY_EXISTS;

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ScheduleIsBookedException.class)
    protected ResponseEntity<Object> handleScheduleIsBooked(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = BOOKED_SCHEDULE;

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(DepartmentHasAlreadyExistedException.class)
    protected ResponseEntity<Object> handleDepartmentHasAlreadyExisted(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = DEPARTMENT_EXISTS;

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(BusyPassportException.class)
    protected ResponseEntity<Object> handlePatientPassportBusy(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = BUSY_PASSPORT;

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}
