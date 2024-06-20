package com.binnacle.api.request;

import lombok.Getter;

@Getter
public class LogFilterRequest {
    private int projectId;
    private int taskId;
    private int subtaskId;
}
