package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) throws Exception {
        Account existingUser = accountRepository.findById(message.getPostedBy()).orElseThrow(() -> new Exception("Account not found"));
        
        if (message.getMessageText().length() == 0 || message.getMessageText().length() > 255) {
            throw new Exception("Message length invalid");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) throws Exception {
        return messageRepository.findById(messageId).orElseThrow(() -> new Exception("Message not found"));
    }

    public String deleteMessageById(int messageId) throws Exception {
        Message message = getMessageById(messageId);

        messageRepository.deleteById(message.getMessageId());

        return "1";
    }

    public String updateMessageById(int messageId, Message message) throws Exception {
        Message existingMessage = getMessageById(messageId);
        
        if (message.getMessageText().length() == 0 || message.getMessageText().length() > 255) {
            throw new Exception("Message length invalid");
        }

        existingMessage.setMessageText(message.getMessageText());

        messageRepository.save(existingMessage);

        return "1";
    }

    public List<Message> getAllMessagesByAccountId(int accountId) {
        List<Message> accountMessages = messageRepository.findByPostedBy(accountId);

        return accountMessages;
    }
}
