package com.binnacle.api.repository.contract;

import com.binnacle.api.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository {
    UserEntity findByUserName(String username);
}
