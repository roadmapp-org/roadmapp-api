package com.binnacle.api.utils.exceptions;

import java.util.ArrayList;
import java.util.List;

public class AppException extends Exception {
    private List<String> descriptions;

    public List<String> getDescriptions() {
        return descriptions;
    }
    public AppException(String e) {
        super(e);
    }
    public AppException(String message, String... descriptions) {
        super(message);
        List<String> messageList = new ArrayList<>();
        for (String description : descriptions) {
            messageList.add(description);
        }
        this.descriptions = messageList;
    }

}
