package com.binnacle.api.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    private int id;
    private String name;
    private Project project;
    private List<Subtask> subtaskList;
    private List<Log> logList;
    private List<Utility> utilityList;
}
