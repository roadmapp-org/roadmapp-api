package com.binnacle.api.entity;

import com.binnacle.api.model.Log;
import com.binnacle.api.model.Task;
import com.binnacle.api.model.Utility;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "projects")
public class ProjectEntity {

    public ProjectEntity(int id, String name, String owner, boolean active) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.active = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String owner;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<TaskEntity> taskList;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<LogEntity> logList;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<UtilityEntity> utilList;
    private boolean active;

}
