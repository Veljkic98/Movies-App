package com.movieserver.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.movieserver.domain.dto.request.CreateActorRequest;
import com.movieserver.domain.dto.response.ActorView;
import com.movieserver.domain.mapper.ActorMapper;
import com.movieserver.model.Actor;

import org.junit.Before;
import org.junit.Test;

public class ActorMapperTest {

    private ActorMapper mapper;

    @Before
	public void init() {
		mapper = new ActorMapper();
	}

    @Test
    public void toEntity_fieldsOk_actor() {

        CreateActorRequest request = new CreateActorRequest("Veljko", "Veljkovic");

        Actor actor = mapper.toEntity(request);

        // assert
        assertNotNull(actor);
        assertEquals(actor.getFirstName(), request.getFirstName());
        assertEquals(actor.getLastName(), request.getLastName());
    }

    @Test
    public void toDto_fieldsOk_actorView() {

        Actor actor = new Actor(1L, "Veljko", "Veljkovic");

        ActorView view = mapper.toDto(actor);

        // assert
        assertNotNull(view);
        assertEquals(actor.getFirstName(), view.getFirstName());
        assertEquals(actor.getLastName(), view.getLastName());
        assertEquals(actor.getId(), view.getId());
    }

    @Test
    public void toEntityList_listNotEmpty_entityList() {

        List<CreateActorRequest> dtos = new ArrayList<>(){{
            add(new CreateActorRequest("Veljko", "Veljkovic"));
            add(new CreateActorRequest("Nikola", "Nikolic"));
        }};

        List<Actor> actors = mapper.toEntityList(dtos);

        // assert
        assertEquals(actors.size(), dtos.size());
    }

    @Test
    public void toDtoList_listNotEmpty_dtoList() {

        List<Actor> dtos = new ArrayList<>(){{
            add(new Actor(1L, "Veljko", "Veljkovic"));
            add(new Actor(2L, "Nikola", "Nikolic"));
        }};

        List<ActorView> actors = mapper.toDtoList(dtos);

        // assert
        assertEquals(actors.size(), dtos.size());
    }
}
