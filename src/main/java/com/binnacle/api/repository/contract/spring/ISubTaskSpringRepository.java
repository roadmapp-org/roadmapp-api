package com.binnacle.api.repository.contract.spring;

import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.entity.SubtaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISubTaskSpringRepository extends JpaRepository<SubtaskEntity, Integer> {
    Optional<SubtaskEntity> findByName(String subtaskName);
}
