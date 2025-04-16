package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

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
     * The creation of the message will be successful if and only if the message_text is not blank, is under 255 characters, and posted_by refers to a real, existing user. If successful, the response body should contain a JSON of the message, including its message_id. The response status should be 200, which is the default. The new message should be persisted to the database.
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
     * @return Messages from Database
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message findMessageById(Integer id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    public Integer deleteMessage(Integer id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            messageRepository.deleteById(id);
            return 1;
        }
        return null;
    }
    
    public void updateMessageById(Integer id, Message message) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message updatedMessage = optionalMessage.get();
            updatedMessage.setMessageText(message.getMessageText());
            messageRepository.save(updatedMessage); 
        }
    }
    /**
     * TODO:
     * addMessage
     * getAllMessages
     * findMessageById
     * deleteMessageById
     * updateMessageById
     */
}
