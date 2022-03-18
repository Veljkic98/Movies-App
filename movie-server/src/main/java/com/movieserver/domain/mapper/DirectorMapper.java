package com.movieserver.domain.mapper;

import java.util.List;

import com.movieserver.domain.dto.request.CreateDirectorRequest;
import com.movieserver.domain.dto.response.DirectorView;
import com.movieserver.model.Director;

import org.springframework.stereotype.Service;

@Service
public class DirectorMapper implements Mapper<Director, DirectorView, CreateDirectorRequest> {

    @Override
    public Director toEntity(CreateDirectorRequest dto) {
        
        Director director = new Director(
            dto.getFirstName(), 
            dto.getLastName()
        );

        return director;
    }

    @Override
    public DirectorView toDto(Director entity) {
        
        DirectorView view = new DirectorView(
            entity.getId(), 
            entity.getFirstName(), 
            entity.getLastName()
        );

        return view;
    }

    @Override
    public List<DirectorView> toDtoList(List<Director> entities) {
        
        // TODO Auto-generated method stub
        return null;
    }
    
}
