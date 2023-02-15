package com.task.task.controller.controller_exception;

import com.task.task.model.ErrorConstant;
import com.task.task.model.ErrorMessage;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.validation.FieldError;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        // ErrorMessage for the moment - by design - only accepts strings, is this how we would use the enums with it?
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ErrorConstant.TEC001.key, ErrorConstant.TEC001.value, "some details should go here too", errors);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    // ---------------------------------------------------------
    // We no longer need to handle WebClientResponseException because it is handled in 
    //  inside src\main\java\com\task\task\restTemplate\RestTemplate.java
    // check method getOne() line 33 .onStatus() makes webClient bypass WebClientResponseException
    // and return empty mono, which means we get the treated ErrorMessage from Entity API
    // ---------------------------------------------------------
    // 
    // @ExceptionHandler(WebClientResponseException.class)
    // public ResponseEntity<?> handleWebClientResponseException(WebClientResponseException exception) {
    //     ErrorMessage errorMessage = exception.getResponseBodyAs(ErrorMessage.class);
    //     if (errorMessage != null) {
    //         HttpStatusCode httpStatus = exception.getStatusCode();
    //         return new ResponseEntity<>(errorMessage, httpStatus);
    //         // return errorMessage;
    //     } else {
    //         ErrorMessage fallBackErrorMessage = new ErrorMessage("500",
    //                 "Original errorMessage returned null, further action required", "500",
    //                 "Original errorMessage returned null, further action required");
    //         return new ResponseEntity<ErrorMessage>(fallBackErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<ErrorMessage> handleWebClientRequestException(WebClientRequestException exception) {
        String details = "request method " + exception.getMethod() + "failed with possible cause "
                + exception.getLocalizedMessage();
        ErrorMessage requestErrorMessage = new ErrorMessage("500",
                "request failed with mesage " + exception.getMessage(), "500", details);
        return new ResponseEntity<ErrorMessage>(requestErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
