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

    @PostMapping("/register")
    public ResponseEntity<Object> userRegistration(@RequestBody Account account) {
        Account retrievedAccount = accountService.addAccount(account);
        return ResponseEntity.ok(retrievedAccount);
    }

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

    @PostMapping("/messages")
    public ResponseEntity<Object> createMessage(@RequestBody Message message) {
        if (messageService.addMessage(message) == null) {
            return ResponseEntity.badRequest().body("Bad Request");
        }
        return ResponseEntity.ok(messageService.addMessage(message));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> retriveAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> retrieveMessageByMessageId(@PathVariable Integer messageId) {
        return ResponseEntity.ok(messageService.findMessageById(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByMessageId(@PathVariable Integer messageId) {
        return ResponseEntity.ok(messageService.deleteMessage(messageId));
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        if (messageService.updateMessageById(messageId, message) == null) {
            return ResponseEntity.badRequest().body("Bad Request");
        }
        return ResponseEntity.ok(messageService.updateMessageById(messageId, message));
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> retrieveAllMessagesForUser(@PathVariable Integer accountId) {
        return ResponseEntity.ok(messageService.getAllMessagesByAccountId(accountId));
    }
}
