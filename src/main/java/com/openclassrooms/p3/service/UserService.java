package com.openclassrooms.p3.service;

import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.request.AuthRegisterRequest;
import com.openclassrooms.p3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Retrieve a user by their unique identifier.
     *
     * @param id The identifier of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    public Optional<Users> getUser(final Long id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieve all users.
     *
     * @return Iterable collection of all users.
     */
    public Iterable<Users> getUsers() {
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
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    /**
     * Retrieve a user by their email address.
     *
     * @param email The email address of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    public Optional<Users> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Verify if the provided email is already in use.
     *
     * @param email The email to be verified.
     * @return True if the email is already in use, false otherwise.
     */
    public boolean isEmailInUse(String email) {
        Optional<Users> existingUser = getUserByEmail(email);
        return existingUser.isPresent();
    }

    /**
     * Verify if the provided password matches the stored hashed password.
     *
     * @param user     The user for which to verify the password.
     * @param password The password to be verified.
     * @return True if the password matches, false otherwise.
     */
    public boolean isPasswordValid(Users user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * Save or update a user using information from the registration request.
     *
     * @param registrationRequest The registration request containing user details.
     * @return The saved or updated user.
     */
    public Users saveUserBySignUp(AuthRegisterRequest registrationRequest) {
        Users user = new Users();

        LocalDateTime currentTime = LocalDateTime.now();

        user.setName(registrationRequest.name());
        user.setEmail(registrationRequest.email());
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));
        user.setCreatedAt(currentTime);
        user.setUpdatedAt(currentTime);

        return saveUser(user);
    }

}
