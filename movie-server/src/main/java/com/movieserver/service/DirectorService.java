package com.movieserver.service;

import java.util.List;

import com.movieserver.model.Director;
import com.movieserver.model.User;
import com.movieserver.repository.DirectorRepository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private DirectorRepository directorRepository;

    public List<Director> findAll() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully look up for all directors."
        );
        
        return directorRepository.findAll();
    }
    
}
