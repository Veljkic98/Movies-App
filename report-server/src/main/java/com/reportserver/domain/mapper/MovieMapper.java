package com.reportserver.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import com.reportserver.domain.dto.MovieReport;
import com.reportserver.model.Movie;

import org.springframework.stereotype.Service;

@Service
public class MovieMapper implements Mapper<Movie, MovieReport> {

    @Override
    public MovieReport toReport(Movie entity) {
        
        MovieReport movieReport = new MovieReport(
            entity.getId(), 
            entity.getName(), 
            entity.getGenre(), 
            entity.getLength(), 
            entity.getDirector().getFirstName(), 
            entity.getDirector().getLastName()
        );

        return movieReport;
    }

    @Override
    public List<MovieReport> toReportList(List<Movie> entities) {
        
        return new ArrayList<>() {{
            entities.forEach(e -> {
                MovieReport mr = toReport(e);
                add(mr);
            });
        }};
    }
    
}
