package com.app.app.controller;

import com.app.app.model.Message;
import com.app.app.service.MailService;
import com.app.app.service.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("api")
public class MessageController {

    private final MessageService messageService;
    private final MailService mailService;


    @Inject
    public MessageController(MessageService messageService, MailService mailService) {
        this.messageService = messageService;
        this.mailService = mailService;
    }


    @PostMapping("message")
    public @ResponseBody Message createMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }

    @GetMapping("messages/{email}")
    public @ResponseBody List<Message> getMessagesByEmail(@PathVariable String email, @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        return messageService.getMessagesByEmail(email, page, size);
    }

    @PostMapping("send")
    public @ResponseBody List<Message> sendMessagesByMagicNumber(@RequestBody JsonNode sendRequest) {
        int magicNumber = sendRequest.get("magic_number").asInt();

        List<Message> messagesToSend = messageService.getMessagesByMagicNumber(magicNumber);
        List<Message> sentMessages = mailService.sendMessages(messagesToSend);
        messageService.deleteMessages(sentMessages);

        return sentMessages;
    }
}