package com.binnacle.api.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subtask {
    private int id;
    private String name;
    private Task task;
    private List<Log> logList;
    private List<Utility> utilityList;
}
