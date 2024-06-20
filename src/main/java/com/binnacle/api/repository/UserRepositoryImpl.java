package com.binnacle.api.repository;

import com.binnacle.api.entity.UserEntity;
import com.binnacle.api.repository.contract.IUserRepository;
import com.binnacle.api.repository.contract.spring.IUserSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {
    private final IUserSpringRepository userSpringRepository;
    @Override
    public UserEntity findByUserName(String username) {
        return userSpringRepository.findById(username).orElse(null);
    }
}
