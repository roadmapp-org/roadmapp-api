package com.binnacle.api.repository.contract.spring;

import com.binnacle.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IUserSpringRepository extends JpaRepository<UserEntity, String> {

}
