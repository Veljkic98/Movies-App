package com.movieserver.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import com.movieserver.constants.MovieConstants;
import com.movieserver.domain.dto.request.CreateActorRequest;
import com.movieserver.domain.dto.request.CreateDirectorRequest;
import com.movieserver.domain.dto.request.UpdateMovieRequest;
import com.movieserver.domain.dto.response.MovieView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UpdateMovieControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void updateMovie_fieldsOk_ok() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Branka", "Katic"));
        }};

        UpdateMovieRequest request = new UpdateMovieRequest(
            MovieConstants.ID_MOVIE_EXISTS, 
            MovieConstants.ID_MOVIE_EXISTS + " 2", 
            MovieConstants.GENRE_COMEDY, 
            154, 
            director, 
            actors
        );

        HttpHeaders headers = new HttpHeaders();
        
        headers.set("Authorization", "Bearer " + MovieConstants.JWT);
        HttpEntity<UpdateMovieRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<?> responseEntity = restTemplate.exchange(
            "/movie-management/movies", 
            HttpMethod.PUT, 
            entity, 
            MovieView.class
        );

        // assert
        assertEquals(responseEntity.getStatusCode().value(), HttpStatus.CREATED.value());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void updateMovie_idNotExists_ok() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Branka", "Katic"));
        }};

        UpdateMovieRequest request = new UpdateMovieRequest(
            MovieConstants.ID_MOVIE_NOT_EXISTS, 
            MovieConstants.CREATE_MOVIE_NAME_OK + " 2", 
            MovieConstants.GENRE_COMEDY, 
            154, 
            director, 
            actors
        );

        HttpHeaders headers = new HttpHeaders();
        
        headers.set("Authorization", "Bearer " + MovieConstants.JWT);
        HttpEntity<UpdateMovieRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<?> responseEntity = restTemplate.exchange(
            "/movie-management/movies", 
            HttpMethod.PUT, 
            entity, 
            MovieView.class
        );

        // assert
        assertEquals(responseEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
        assertNull(responseEntity.getBody());
    }
    
}
