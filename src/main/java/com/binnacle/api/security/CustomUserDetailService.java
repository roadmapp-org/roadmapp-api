package com.binnacle.api.security;

import com.binnacle.api.entity.UserEntity;
import com.binnacle.api.repository.contract.IUserRepository;
import com.binnacle.api.utils.errors.ErrorCodes;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity =  userRepository.findByUserName(username);
        if(userEntity==null)
            throw new UsernameNotFoundException(ErrorCodes.RECORD_NOT_FOUND);

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole())
                .build();
    }
}
