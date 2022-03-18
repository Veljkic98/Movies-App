package com.authserver.domain.mapper;

import java.util.List;

import com.authserver.domain.dto.request.CreateUserRequest;
import com.authserver.domain.dto.response.CreatedUser;
import com.authserver.model.User;

import org.springframework.stereotype.Service;

@Service
public class CreateUserMapper implements Mapper<User, CreatedUser, CreateUserRequest> {

    @Override
    public User toEntity(CreateUserRequest dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreatedUser toDto(User entity) {
        
        CreatedUser createdUser = new CreatedUser(
            entity.getId(), 
            entity.getFirstName(), 
            entity.getLastName(), 
            entity.getEmail()
        );

        return createdUser;
    }

    @Override
    public List<CreatedUser> toDtoList(List<User> entities) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
