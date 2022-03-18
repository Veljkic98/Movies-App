package com.movieserver.repository;

import com.movieserver.model.Director;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    Director findByFirstNameAndLastName(String firstName, String lastName);
    
}
