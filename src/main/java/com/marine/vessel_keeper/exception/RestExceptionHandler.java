package com.marine.vessel_keeper.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value =
            {UserException.class,
                    SeamanException.class,
                    VesselException.class,
                    SeamanCertificateException.class,
                    VoyageException.class})
    protected ResponseEntity<Object> handleIllegalState(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
