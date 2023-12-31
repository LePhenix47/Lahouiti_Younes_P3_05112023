package com.openclassrooms.p3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.p3.model.Message;

/**
 * Repository interface for managing Message entities in the database.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}