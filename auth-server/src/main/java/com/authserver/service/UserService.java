package com.authserver.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import com.authserver.domain.dto.request.CreateUserRequest;
import com.authserver.domain.dto.request.UserLoginRequest;
import com.authserver.model.Authority;
import com.authserver.model.User;
import com.authserver.repository.AuthorityRepository;
import com.authserver.repository.UserRepository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final Logger LOG = LogManager.getLogger(this.getClass());
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User create(CreateUserRequest request) {
        
        if (userRepository.findByEmail(request.getEmail()) != null) {
            LOG.log(Level.INFO, "Trying to create user with existing email '" + request.getEmail() + "'.");

            throw new ValidationException("Username exist!");
        }

        if (!request.getPassword().equals(request.getRePassword())) {
            LOG.log(
                Level.INFO, 
                "Trying to create user with email '" + request.getEmail() 
                    + "', but password and repassword are differente."
            );

            throw new ValidationException("Passwords don't match!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        Authority auth = authRepository.findByName("ROLE_USER");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(auth);
        user.setAuthorities(authorities);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        LOG.log(Level.INFO, "Successfully user registration completted with user id: '" + user.getId() + "'.");

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user != null)
            return user;

        // throw new EntityNotFoundException();
        return null;
    }

    public User login(UserLoginRequest request) {

        Authentication authenticate = authenticationManager
            .authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()
                )
            );

        User user = (User) authenticate.getPrincipal();

        LOG.log(Level.INFO, "User with id: '" + user.getId() + "' successfully logged in.");
        
        return user;
    }

}
