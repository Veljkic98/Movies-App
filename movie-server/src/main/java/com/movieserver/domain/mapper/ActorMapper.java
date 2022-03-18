package com.movieserver.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import com.movieserver.domain.dto.request.CreateActorRequest;
import com.movieserver.domain.dto.response.ActorView;
import com.movieserver.model.Actor;

import org.springframework.stereotype.Service;

@Service
public class ActorMapper implements Mapper<Actor, ActorView, CreateActorRequest> {

    @Override
    public Actor toEntity(CreateActorRequest dto) {

        Actor actor = new Actor(
            dto.getFirstName(),
            dto.getLastName()
        );

        return actor;
    }

    public List<Actor> toEntityList(List<CreateActorRequest> dtos) {

        List<Actor> actors = new ArrayList<>(){{
            for (CreateActorRequest dto : dtos) 
                add(toEntity(dto));
        }};

        return actors;
    }

    @Override
    public ActorView toDto(Actor entity) {
        
        ActorView view = new ActorView(
            entity.getId(), 
            entity.getFirstName(), 
            entity.getLastName()
        );
        
        return view;
    }

    @Override
    public List<ActorView> toDtoList(List<Actor> entities) {
        
        List<ActorView> actors = new ArrayList<>(){{
            for (Actor actor : entities) 
                add(toDto(actor));
        }};

        return actors;
    }
    
}
