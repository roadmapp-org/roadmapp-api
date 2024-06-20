package com.binnacle.api.request;

import com.binnacle.api.utils.errors.ErrorDescriptions;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
public class CreateUpdateLogRequest {

    private int id;
    private int projectId;
    private int taskId;
    private int subtaskId;
    @NotBlank(message = ErrorDescriptions.NOT_BLANK)
    @Size(min = 5, max = 5000, message = ErrorDescriptions.UP_TO_50_CHARACTERS)
    private String log;
}
