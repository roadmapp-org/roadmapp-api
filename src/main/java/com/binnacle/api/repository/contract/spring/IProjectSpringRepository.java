package com.binnacle.api.repository.contract.spring;

import com.binnacle.api.entity.ProjectEntity;
import com.binnacle.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProjectSpringRepository extends JpaRepository<ProjectEntity, Integer> {
    Optional<ProjectEntity> findByName(String projectName);
    //Optional<List<ProjectEntity>> findByOwner(String projectName);
    @Query(value = "SELECT * FROM projects WHERE owner = :name", nativeQuery = true)
    Optional<List<ProjectEntity>> findByOwner(@Param("name") String owner);

}
