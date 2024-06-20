package com.binnacle.api.repository;

import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.entity.TaskEntity;
import com.binnacle.api.repository.contract.IProjectRepository;
import com.binnacle.api.repository.contract.ITaskRepository;
import com.binnacle.api.repository.contract.spring.IProjectSpringRepository;
import com.binnacle.api.repository.contract.spring.ITaskSpringRepository;
import com.binnacle.api.response.task.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements ITaskRepository {
    private final ITaskSpringRepository taskSpringRepository;
    private final JdbcTemplate jdbcTemplate;
    @Override
    public TaskEntity findByTaskName(String projectName) {
        return taskSpringRepository.findByName(projectName).orElse(null);
    }

    @Override
    public TaskEntity save(TaskEntity project) {
        return taskSpringRepository.save(project);
    }

    @Override
    public TaskEntity findById(int id) {
        return taskSpringRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(TaskEntity taskEntity) {
        taskSpringRepository.delete(taskEntity);
    }

    @Override
    public List<TaskResponse> getAllByOwner(String owner) {
        String sqlQuery = "SELECT t.* FROM tasks t INNER JOIN projects p WHERE t.project_id = p.id AND p.owner = :name";
        return jdbcTemplate.query(sqlQuery, new Object[]{owner}, (rs, rowNum) ->
                new TaskResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("project_id"),
                        rs.getBoolean("active")
                ));
    }
}
