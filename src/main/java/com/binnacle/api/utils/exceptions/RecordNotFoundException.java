package com.binnacle.api.utils.exceptions;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RecordNotFoundException extends AppException {
    private List<String> descriptions;
    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, String... descriptions) {
        super(message, descriptions);
    }

}
