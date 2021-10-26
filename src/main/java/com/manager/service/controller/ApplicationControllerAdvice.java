package com.manager.service.controller;

import com.manager.service.exception.ApiErrors;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map(objectErro -> objectErro.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(messages);
    }
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrors> handleResponseStatusException(ResponseStatusException ex) {
        String messageError = ex.getReason();
        HttpStatus status = ex.getStatus();
        ApiErrors apiErrors = new ApiErrors(messageError);
        return new ResponseEntity<>(apiErrors,status);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors internalError(Exception ex) {
        String msg = ((DataIntegrityViolationException) ex).getMostSpecificCause().getMessage();
        return new ApiErrors(msg);
    }
    
}
