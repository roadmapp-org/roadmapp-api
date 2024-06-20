package com.binnacle.api.repository.contract.spring;

import com.binnacle.api.entity.LogEntity;
import com.binnacle.api.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogSpringRepository extends JpaRepository<LogEntity, Integer> {



}
