package com.openclassrooms.p3.service;

import com.openclassrooms.p3.model.Message;
import com.openclassrooms.p3.payload.request.MessageRequest;
import com.openclassrooms.p3.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.Data;

/**
 * Service class for managing messages.
 */
@Data
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Retrieve a message by its unique identifier.
     *
     * @param id The identifier of the message.
     * @return An Optional containing the message if found, or empty if not.
     */
    public Optional<Message> getMessage(final Long id) {
        return messageRepository.findById(id);
    }

    /**
     * Retrieve all messages.
     *
     * @return Iterable collection of all messages.
     */
    public Iterable<Message> getMessages() {
        return messageRepository.findAll();
    }

    /**
     * Delete a message by its unique identifier.
     *
     * @param id The identifier of the message to be deleted.
     */
    public void deleteMessage(final Long id) {
        messageRepository.deleteById(id);
    }

    /**
     * Save or update a message.
     *
     * @param message The message to be saved or updated.
     * @return The saved or updated message.
     */
    public Message saveMessage(MessageRequest message) {
        Message newMessage = new Message();

        LocalDateTime currentTime = LocalDateTime.now();

        newMessage.setUserId(message.user_id());
        newMessage.setRentalId(message.rental_id());
        newMessage.setMessage(message.message());
        newMessage.setCreatedAt(currentTime);
        newMessage.setUpdatedAt(currentTime);

        return messageRepository.save(newMessage);
    }
}
