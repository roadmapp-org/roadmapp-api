package com.binnacle.api.request;

import com.binnacle.api.utils.errors.ErrorDescriptions;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class CreateUpdateTaskRequest {

    private int id;
    private int projectId;
    @NotBlank(message = ErrorDescriptions.NOT_BLANK)
    @Size(max = 50, message = ErrorDescriptions.UP_TO_50_CHARACTERS)
    private String name;
    private boolean active;
}
