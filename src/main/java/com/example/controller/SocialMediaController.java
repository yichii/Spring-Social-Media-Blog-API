package com.example.controller;

import java.util.List;
import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.MessageService;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity userRegistration(@RequestBody Account account) {
        if (accountService.addAccount(account) == null) {
            return ResponseEntity.badRequest().body("Bad Request");
        }
        return ResponseEntity.ok(accountService.addAccount(account));
    }

    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody Account account) {
        if (accountService.getAccountByUsernameAndPassword(account) == null) {
            return ResponseEntity.status(401).body("Try again");
        }
        return ResponseEntity.ok(accountService.getAccountByUsernameAndPassword(account));
    }

    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody Message message) {
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
