package com.binnacle.api.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "utilities")
public class UtilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;
    @ManyToOne
    @JoinColumn(name = "subtask_id")
    private SubtaskEntity subtask;
    private LocalDate date;
    private String name;
    private String value;
    private boolean active;
}
