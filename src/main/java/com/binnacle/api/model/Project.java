package com.binnacle.api.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    private int id;
    private String owner;
    private String name;
    private List<Task> taskList;
    private List<Log> logList;
    private List<Utility> utilityList;
}
