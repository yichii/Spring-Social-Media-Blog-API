package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class MessageService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * The creation of the message will be successful if and only if the
     * message_text is not blank, is under 255 characters, and posted_by refers to a
     * real, existing user. If successful, the response body should contain a JSON
     * of the message, including its message_id. The response status should be 200,
     * which is the default. The new message should be persisted to the database.
     * 
     * @param message
     * @return Created Message
     */
    public Message addMessage(Message message) {
        boolean postedByExists = accountRepository.existsById(message.getPostedBy());
        if (message.getMessageText().isEmpty() || message.getMessageText().length() > 255 || !postedByExists) {
            return null;
        }
        return messageRepository.save(message);
    }

    /**
     * Gets all messages retrieved from the database
     * 
     * @return Messages from Database
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /***
     * The response body should contain a JSON representation of the message
     * identified by the messageId. It is expected for the response body to simply
     * be empty if there is no such message. The response status should always be
     * 200, which is the default.
     * 
     * @param id
     * @return The message if found in database but not if nothing is found.
     */
    public Message findMessageById(Integer id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    /**
     * The deletion of an existing message should remove an existing message from
     * the database.
     * 
     * @param id
     * @return If the message existed, the response body should contain the
     *         number of rows updated (1) and null if nothing was deleted.
     */
    public Integer deleteMessage(Integer id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            messageRepository.deleteById(id);
            return 1;
        }
        return null;
    }

    /**
     * The update of a message should be successful if and only if the message id
     * already exists and the new messageText is not blank and is not over 255
     * characters.
     * 
     * @param id      The messageId
     * @param message JSON representation of the message that doesn't contain that
     *                does not contain messageId
     * @return If the update is successful, the response body should contain the
     *         number of rows updated (1) and null if nothing was updated
     */
    public Integer updateMessageById(Integer id, Message message) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent() && message.getMessageText().length() < 255
                && !message.getMessageText().isEmpty()) {
            Message updatedMessage = optionalMessage.get();
            updatedMessage.setMessageText(message.getMessageText());
            messageRepository.save(updatedMessage);
            return 1;
        }
        return null;
    }

    /**
     * Retrieves all messages from a particular user based on accountId
     * 
     * @param accountId
     * @return The response body should contain a JSON representation of a list
     *         containing all messages posted by a particular user, which is retrieved from the
     *         database. It is expected for the list to simply be empty if there are no messages.
     */
    public List<Message> getAllMessagesByAccountId(Integer accountId) {
        return messageRepository.findAllMessagesByAccountId(accountId);
    }
}
