package com.binnacle.api.repository.contract;

import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.entity.TaskEntity;
import com.binnacle.api.response.task.TaskResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository {
    TaskEntity findByTaskName(String taskName);
    TaskEntity save(TaskEntity project);

    TaskEntity findById(int id);

    void delete(TaskEntity taskEntity);
    List<TaskResponse> getAllByOwner(String owner);
}
