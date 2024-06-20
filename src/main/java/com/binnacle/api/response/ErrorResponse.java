package com.binnacle.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    public ErrorResponse(String code, String... descriptions) {
        this.code = code;
        List<String> messageList = new ArrayList<>();
        for (String description : descriptions) {
            messageList.add(description);
        }
        this.description = messageList;
    }
    public ErrorResponse(String code, String descriptions) {
        this.code = code;
        List<String> messageList = new ArrayList<>();
        messageList.add(descriptions);
        this.description = messageList;
    }
    private final String code;
    private final List<String> description;

}
