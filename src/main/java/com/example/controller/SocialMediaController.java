package com.example.controller;

import java.util.List;
import com.example.entity.Message;
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

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    @Autowired
    private MessageService messageService;
    private AccountService accountService;

    // @PostMapping("/register")
    // @PostMapping("/login")
    @PostMapping(value = "/messages")
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

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> retrieveMessageByMessageId(@PathVariable Integer messageId) {
        return messageService.findMessageById(messageId).map(ResponseEntity.ok());
    }
    
    // @DeleteMapping("/messages/{message_id}")
    // public ResponseEntity deleteMessageByMessageId(@PathVariable Integer id) {
    //     messageService.deleteMessage(id);
    //     return ResponseEntity.ok().build();
    // }

    // @PatchMapping("/messages/{message_id}")
    // @GetMapping("accounts/{account_id}/messages")
}
