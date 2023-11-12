package com.openclassrooms.p3.service;

import com.openclassrooms.p3.model._User;
import com.openclassrooms.p3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
