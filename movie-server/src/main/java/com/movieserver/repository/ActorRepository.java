package com.movieserver.repository;

import com.movieserver.model.Actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    Actor findByFirstNameAndLastName(String firstName, String lastName);
    
}
