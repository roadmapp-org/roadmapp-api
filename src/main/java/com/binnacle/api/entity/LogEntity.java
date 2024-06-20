package com.binnacle.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "logs")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private ProjectEntity project;
    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private TaskEntity task;
    @ManyToOne
    @JoinColumn(name = "subtask_id")
    @JsonIgnore
    private SubtaskEntity subtask;
    private String date;
    private String log;
    private boolean active;
}
