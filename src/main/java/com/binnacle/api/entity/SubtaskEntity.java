package com.binnacle.api.entity;

import com.binnacle.api.model.Log;
import com.binnacle.api.model.Project;
import com.binnacle.api.model.Task;
import com.binnacle.api.model.Utility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subtasks")
public class SubtaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private TaskEntity task;
    @OneToMany(mappedBy = "subtask", cascade = CascadeType.ALL)
    private List<LogEntity> logList;
    @OneToMany(mappedBy = "subtask", cascade = CascadeType.ALL)
    private List<UtilityEntity> utilList;
    private boolean active;
}
