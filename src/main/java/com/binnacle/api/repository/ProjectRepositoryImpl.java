package com.binnacle.api.repository;

import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.repository.contract.IProjectRepository;
import com.binnacle.api.repository.contract.spring.IProjectSpringRepository;
import com.binnacle.api.response.project.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements IProjectRepository {
    private final IProjectSpringRepository projectSpringRepository;
    private final JdbcTemplate jdbcTemplate;
    @Override
    public ProjectEntity findByProjectName(String projectName) {
        return projectSpringRepository.findByName(projectName).orElse(null);
    }

    @Override
    public ProjectEntity save(ProjectEntity project) {
        return projectSpringRepository.save(project);
    }

    @Override
    public ProjectEntity findById(int id) {
        return projectSpringRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(ProjectEntity projectEntity) {
        projectSpringRepository.delete(projectEntity);
    }

    /*@Override
    public List<ProjectEntity> getAllByOwner(String owner) {
        return projectSpringRepository.findByOwner(owner).get();
    }*/

    @Override
    public List<ProjectResponse> getAllByOwner(String owner) {
        //return projectSpringRepository.findByOwner(owner).get();
        String sqlQuery = "SELECT * FROM projects WHERE owner = :name";
        return jdbcTemplate.query(sqlQuery, new Object[]{owner}, (rs, rowNum) ->
                new ProjectResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("owner"),
                        rs.getBoolean("active")
                ));
    }
}
