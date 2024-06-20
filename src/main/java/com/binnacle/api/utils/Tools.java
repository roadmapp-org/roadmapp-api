package com.binnacle.api.utils;

import com.binnacle.api.response.DataResponse;
import com.binnacle.api.response.ErrorResponse;
import com.binnacle.api.response.PersistResponse;
import com.binnacle.api.utils.errors.ErrorCodes;
import com.binnacle.api.utils.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class Tools {
    public static ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.VALIDATION_ERROR, errors);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        return null;
    }

    public static PersistResponse getBadRequest(AppException e, String descriptions) {
        return getPersistErrorResponse(e,descriptions,HttpStatus.BAD_REQUEST);
    }

    public static PersistResponse getBadRequest(Exception e, String descriptions) {
        return getPersistErrorResponse(e,descriptions,HttpStatus.BAD_REQUEST);
    }

    public static PersistResponse getUnauthorized(AppException e, String descriptions) {
        return getPersistErrorResponse(e,descriptions,HttpStatus.UNAUTHORIZED);
    }

    private static PersistResponse getPersistErrorResponse(AppException e, String descriptions, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),e.getDescriptions());
        if(!descriptions.equals(""))
            errorResponse = new ErrorResponse(e.getMessage(),descriptions);
        return new PersistResponse(
                Results.ERROR,
                errorResponse,
                "",
                HttpStatus.BAD_REQUEST
        );
    }

    private static PersistResponse getPersistErrorResponse(Exception e, String descriptions, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),descriptions);
        return new PersistResponse(
                Results.ERROR,
                errorResponse,
                "",
                HttpStatus.BAD_REQUEST
        );
    }

    public static DataResponse getDataResponseError(Exception e, String descriptions) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),descriptions);
        return new DataResponse(
                Results.ERROR,
                errorResponse,
                "",
                HttpStatus.BAD_REQUEST
        );

    }
}
