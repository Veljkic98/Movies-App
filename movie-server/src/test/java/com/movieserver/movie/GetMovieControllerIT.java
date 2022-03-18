package com.movieserver.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import com.movieserver.constants.MovieConstants;
import com.movieserver.model.Movie;

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
public class GetMovieControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll__ok() {
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.set("Authorization", "Bearer " + MovieConstants.JWT);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<?> responseEntity = restTemplate.exchange(
            "/movie-management/movies", 
            HttpMethod.GET, 
            entity, 
            List.class
        );

        // assert
        assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void getOne_movieExists_ok() {

        HttpHeaders headers = new HttpHeaders();
        
        headers.set("Authorization", "Bearer " + MovieConstants.JWT);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<?> responseEntity = restTemplate.exchange(
            "/movie-management/movies/" + MovieConstants.ID_MOVIE_EXISTS, 
            HttpMethod.GET, 
            entity, 
            Movie.class
        );

        // assert
        assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void getOne_movieNotExists_exception() {

        HttpHeaders headers = new HttpHeaders();
        
        headers.set("Authorization", "Bearer " + MovieConstants.JWT);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<?> responseEntity = restTemplate.exchange(
            "/movie-management/movies/" + MovieConstants.ID_MOVIE_NOT_EXISTS, 
            HttpMethod.GET, 
            entity, 
            String.class
        );

        // assert
        assertEquals(responseEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
        assertNull(responseEntity.getBody());
    }
    
}
