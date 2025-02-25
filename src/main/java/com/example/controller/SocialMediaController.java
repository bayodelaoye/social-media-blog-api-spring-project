package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            // Create the account using the service
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(createdAccount);
        } catch (Exception e) {
            // Handle any errors during account creation
            if (e.getMessage().equals("Username already taken")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            Account loginAccount = accountService.login(account);
            return ResponseEntity.status(HttpStatus.OK).body(loginAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(allMessages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        try {
            Message message = messageService.getMessageById(messageId);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessageById(@PathVariable int messageId) {
        try {
            String message = messageService.deleteMessageById(messageId);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMessageById(@PathVariable int messageId, @RequestBody Message message) {
        try {
            String returnedMessage = messageService.updateMessageById(messageId, message);
            return ResponseEntity.status(HttpStatus.OK).body(returnedMessage);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int accountId) {
        try {
            List<Message> messages = messageService.getAllMessagesByAccountId(accountId);
            return ResponseEntity.status(HttpStatus.OK).body(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        
    }
}
