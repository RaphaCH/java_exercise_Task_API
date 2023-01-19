package com.task.task.controller.validation;

import com.task.task.mapper.ErrorMessageMapper;
import com.task.task.model.CustomException;
import com.task.task.model.ErrorMessage;
import com.task.task.model.Response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;

@ControllerAdvice
public class EmployeeControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<Response>(new Response(false, "400 - Bad Request", HttpStatus.BAD_REQUEST, errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> handleCustomException(CustomException exception) {
        ErrorMessage errorMessage = ErrorMessageMapper.toErrorMessage(exception);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(errorMessage.getStatusCode()));
        return new ResponseEntity<ErrorMessage>(errorMessage, httpStatus);
    }

   
}
