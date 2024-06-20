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
@Table(name = "tasks")
public class TaskEntity {

    public TaskEntity(int id, String name, int projectId, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.project = ProjectEntity.builder().id(projectId).build();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private ProjectEntity project;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<SubtaskEntity> subtaskList;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<LogEntity> logList;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<UtilityEntity> utilList;
    private boolean active;
}
