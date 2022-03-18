package com.movieserver.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import com.movieserver.constants.MovieConstants;
import com.movieserver.domain.dto.request.CreateActorRequest;
import com.movieserver.domain.dto.request.CreateDirectorRequest;
import com.movieserver.domain.dto.request.CreateMovieRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
public class CreateMovieControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createMovieController_fieldsOk_created() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Branka", "Katic"));
        }};

        CreateMovieRequest request = new CreateMovieRequest(
            MovieConstants.CREATE_MOVIE_NAME_OK, 
            MovieConstants.GENRE_SCI_FI, 
            122, 
            director, 
            actors
        );

        HttpHeaders headers = new HttpHeaders();
        
        headers.set("Authorization", "Bearer " + MovieConstants.JWT);
        HttpEntity<CreateMovieRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity("/movie-management/movies", entity, CreateMovieRequest.class);

        // assert
        assertEquals(responseEntity.getStatusCode().value(), HttpStatus.CREATED.value());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void createMovie_nameExists_conflict() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Milos", "Bikovic"));
        }};

        CreateMovieRequest request = new CreateMovieRequest(
            MovieConstants.CREATE_MOVIE_NAME_NOT_OK, 
            MovieConstants.GENRE_SCI_FI, 
            122, 
            director, 
            actors
        );

        HttpHeaders headers = new HttpHeaders();
        
        headers.set("Authorization", "Bearer " + MovieConstants.JWT);
        HttpEntity<CreateMovieRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity("/movie-management/movies", entity, CreateMovieRequest.class);

        assertEquals(responseEntity.getStatusCode().value(), HttpStatus.CONFLICT.value());
        assertNull(responseEntity.getBody());
    }
    
}
