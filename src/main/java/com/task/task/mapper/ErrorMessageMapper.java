package com.task.task.mapper;

import com.task.task.model.CustomException;
import com.task.task.model.ErrorMessage;

public class ErrorMessageMapper {

    public static ErrorMessage toErrorMessage(CustomException customException) {
        if(customException == null) return null;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(customException.getStatusCode());
        errorMessage.setErrorMessage(customException.getErrorMessage());
        errorMessage.setSubCode(customException.getSubcode());
        errorMessage.setDetails(customException.getDetails());
        return errorMessage;
    }
}
