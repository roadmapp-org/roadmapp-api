package com.binnacle.api.utils.exceptions;
import lombok.Getter;

import java.util.List;

@Getter
public class ActionNotAllowedException extends AppException {
    private List<String> descriptions;
    public ActionNotAllowedException(String message) {
        super(message);
    }

    public ActionNotAllowedException(String message, String... descriptions) {
        super(message, descriptions);
    }

}
