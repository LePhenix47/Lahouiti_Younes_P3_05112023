package com.openclassrooms.p3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.p3.model.User;

/**
 * Repository interface for managing User entities in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}