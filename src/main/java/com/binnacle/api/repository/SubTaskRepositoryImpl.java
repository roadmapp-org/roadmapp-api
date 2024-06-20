package com.binnacle.api.repository;

import com.binnacle.api.entity.SubtaskEntity;
import com.binnacle.api.entity.TaskEntity;
import com.binnacle.api.repository.contract.ISubTaskRepository;
import com.binnacle.api.repository.contract.spring.ISubTaskSpringRepository;
import com.binnacle.api.repository.contract.spring.ITaskSpringRepository;
import com.binnacle.api.response.subtask.SubtaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubTaskRepositoryImpl implements ISubTaskRepository {
    private final ISubTaskSpringRepository subTaskSpringRepository;
    private final JdbcTemplate jdbcTemplate;
    @Override
    public SubtaskEntity findBySubTaskName(String projectName) {
        return subTaskSpringRepository.findByName(projectName).orElse(null);
    }
    @Override
    public SubtaskEntity save(SubtaskEntity project) {
        return subTaskSpringRepository.save(project);
    }

    @Override
    public SubtaskEntity findById(int id) {
        return subTaskSpringRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(SubtaskEntity subtaskEntity) {
        subTaskSpringRepository.delete(subtaskEntity);
    }

    @Override
    public List<SubtaskResponse> getAllByOwner(String owner) {
        String sqlQuery = "SELECT s.* FROM subtasks s, tasks t, projects p WHERE s.task_id = t.id AND t.project_id = p.id AND p.owner = :name";
        return jdbcTemplate.query(sqlQuery, new Object[]{owner}, (rs, rowNum) ->
                new SubtaskResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("task_id"),
                        rs.getBoolean("active")
                ));
    }
}
