package com.binnacle.api.utils.exceptions;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class AlreadyDefinedException extends AppException {
    public AlreadyDefinedException(String message) {
        super(message);
    }

    public AlreadyDefinedException(String message, String... descriptions) {
        super(message, descriptions);
    }

}
