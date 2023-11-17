package com.openclassrooms.p3.service;

import com.openclassrooms.p3.configuration.SpringSecurityConfig;
import com.openclassrooms.p3.model._User;
import com.openclassrooms.p3.payload.request.AuthRegisterRequest;
import com.openclassrooms.p3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.Data;

/**
 * Service class for managing users.
 */
@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpringSecurityConfig securityConfig;

    /**
     * Retrieve a user by their unique identifier.
     *
     * @param id The identifier of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    public Optional<_User> getUser(final Long id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieve all users.
     *
     * @return Iterable collection of all users.
     */
    public Iterable<_User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Delete a user by their unique identifier.
     *
     * @param id The identifier of the user to be deleted.
     */
    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Save or update a user.
     *
     * @param user The user to be saved or updated.
     * @return The saved or updated user.
     */
    public _User saveUser(_User user) {
        return userRepository.save(user);
    }

    /**
     * Save or update a user using information from the registration request.
     *
     * @param registrationRequest The registration request containing user details.
     * @return The saved or updated user.
     */
    public _User saveUserFromRegistrationRequest(AuthRegisterRequest registrationRequest) {
        _User user = new _User();

        user.setName(registrationRequest.name());
        user.setEmail(registrationRequest.email());
        user.setPassword(securityConfig.encode(request.password()));
        user.setCreatedAt(LocalDateTime.now()); // Set the createdAt property to the current timestamp

        return userRepository.save(user);
    }
}
