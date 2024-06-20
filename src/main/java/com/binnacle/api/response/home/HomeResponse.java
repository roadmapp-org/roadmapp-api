package com.binnacle.api.response.home;

import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.entity.SubtaskEntity;
import com.binnacle.api.entity.TaskEntity;
import com.binnacle.api.response.log.LogResponse;
import com.binnacle.api.response.project.ProjectResponse;
import com.binnacle.api.response.subtask.SubtaskResponse;
import com.binnacle.api.response.task.TaskResponse;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HomeResponse {
    private List<ProjectResponse> projectList;
    private List<TaskResponse> taskList;
    private List<SubtaskResponse> subtaskList;
    private List<LogResponse> logList;

}
