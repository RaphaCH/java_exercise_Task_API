package com.task.task.model;

import java.util.Map;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    
    private String statusCode;
    private String errorMessage;
    private String subCode;
    private String details;
    // placeholder property to display array of object errors returned by MethodArgumentNotValidException 
    private Map<String, String> placeholderErrors;

    // while placeholder property above was created, the constructor below was added to avoid errors where ErrorMessage was used
    public ErrorMessage(String statusCode, String errorMessage, String subCode, String details){
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.subCode = subCode;
        this.details = details;
    }

    
}
