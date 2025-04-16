package com.example.controller;

import java.util.List;
import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.MessageService;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialMediaController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    /**
     * Registers an account if conditions are met(See accountService)
     * 
     * @param account
     * @return 409 desired username already exists
     *         400 any other credential requirement not met
     *         200 registration success
     */
    @PostMapping("/register")
    public ResponseEntity<Object> userRegistration(@RequestBody Account account) {
        Account retrievedAccount = accountService.register(account);
        return ResponseEntity.ok(retrievedAccount);
    }

    /**
     * Verifies if account exists in database
     * 
     * @param account
     * @return 404 wrong credentials
     *         200 verfication success
     */
    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        Account retrievedAccount = accountService.login(username, password);
        if (retrievedAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        return ResponseEntity.ok(accountService.login(account.getUsername(), account.getPassword()));
    }

    /**
     * Creates a message if conditions are met(See messageService)
     * 
     * @param message
     * @return 400 message creation condition not met
     *         200 message creation succes
     */
    @PostMapping("/messages")
    public ResponseEntity<Object> createMessage(@RequestBody Message message) {
        if (messageService.addMessage(message) == null) {
            return ResponseEntity.badRequest().body("Bad Request");
        }
        return ResponseEntity.ok(messageService.addMessage(message));
    }

    /**
     * Retrieves all messages
     * 
     * @return 200 regardless of whether there's any retrieved messages
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> retriveAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    /**
     * Retrieves a message based on messageId
     * 
     * @param messageId
     * @return 200 regardless of whether there's any retrieved message from
     *         messageId
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> retrieveMessageByMessageId(@PathVariable Integer messageId) {
        return ResponseEntity.ok(messageService.findMessageById(messageId));
    }

    /**
     * Deletes a message based on messageId
     * 
     * @param messageId
     * @return 200 regardless of whether there's any deleted message from
     *         messageId
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByMessageId(@PathVariable Integer messageId) {
        return ResponseEntity.ok(messageService.deleteMessage(messageId));
    }

    /**
     * Updates a message based on messageId
     * 
     * @param messageId
     * @param message
     * @return 400 message update condition not met
     *         200 message update succes
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Object> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        if (messageService.updateMessageById(messageId, message) == null) {
            return ResponseEntity.badRequest().body("Bad Request");
        }
        return ResponseEntity.ok(messageService.updateMessageById(messageId, message));
    }

    /**
     * Retrieves all messages from particular user
     * 
     * @param accountId
     * @return 200 regardless if theres any retrieved messages from particular user
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> retrieveAllMessagesForUser(@PathVariable Integer accountId) {
        return ResponseEntity.ok(messageService.getAllMessagesByAccountId(accountId));
    }
}
