package com.binnacle.api.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Utility {
    private int id;
    private Project project;
    private Task task;
    private Subtask subtask;
    private LocalDate date;
    private String key;
    private String value;

}
