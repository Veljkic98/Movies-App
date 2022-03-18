package com.movieserver.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.movieserver.domain.dto.request.CreateDirectorRequest;
import com.movieserver.domain.dto.response.DirectorView;
import com.movieserver.domain.mapper.DirectorMapper;
import com.movieserver.model.Director;

import org.junit.Before;
import org.junit.Test;

public class DirectorMapperTest {

    private DirectorMapper mapper;

    @Before
	public void init() {
		mapper = new DirectorMapper();
	}

    @Test
    public void toEntity_fieldsOk_director() {
        CreateDirectorRequest request = new CreateDirectorRequest("Veljko", "Veljkovic");

        Director director = mapper.toEntity(request);

        // assert
        assertNotNull(director);
        assertEquals(director.getFirstName(), request.getFirstName());
        assertEquals(director.getLastName(), request.getLastName());
    }

    @Test
    public void toDto_fieldsOk_directorView() {

        Director director = new Director(1L, "Veljko", "Veljkovic");

        DirectorView view = mapper.toDto(director);

        // assert
        assertNotNull(view);
        assertEquals(director.getFirstName(), view.getFirstName());
        assertEquals(director.getLastName(), view.getLastName());
        assertEquals(director.getId(), view.getId());
    }
    
}
