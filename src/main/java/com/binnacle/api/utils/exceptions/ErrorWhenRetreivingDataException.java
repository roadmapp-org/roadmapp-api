package com.binnacle.api.utils.exceptions;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorWhenRetreivingDataException extends AppException {
    private List<String> descriptions;
    public ErrorWhenRetreivingDataException(String message) {
        super(message);
    }

    public ErrorWhenRetreivingDataException(String message, String... descriptions) {
        super(message, descriptions);
    }

}
