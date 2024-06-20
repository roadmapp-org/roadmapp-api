package com.binnacle.api.repository.contract.spring;

import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITaskSpringRepository extends JpaRepository<TaskEntity, Integer> {
    Optional<TaskEntity> findByName(String projectName);
}
