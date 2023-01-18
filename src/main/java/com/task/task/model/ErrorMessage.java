package com.task.task.model;

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
   
}
