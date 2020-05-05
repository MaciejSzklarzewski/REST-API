package com.app.app.model;

import com.app.app.model.table.MessageByEmail;
import com.app.app.model.table.MessageByMagicNumber;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Message {

    private final UUID id;

    @JsonProperty("magic_number")
    private int magicNumber;
    private String email;
    private String title;
    private String content;


    public Message() {
        this.id = UUID.randomUUID();
    }

    private Message(UUID id, int magic_number, String email, String title, String content) {
        this.id = id;
        this.magicNumber = magic_number;
        this.email = email;
        this.title = title;
        this.content = content;
    }

    public Message(int magic_number, String email, String title, String content) {
        this.id = UUID.randomUUID();
        this.magicNumber = magic_number;
        this.email = email;
        this.title = title;
        this.content = content;
    }

    public static Message fromMessageByEmail(MessageByEmail messageByEmail) {
        return new Message(
                messageByEmail.getId(),
                messageByEmail.getMagicNumber(),
                messageByEmail.getEmail(),
                messageByEmail.getTitle(),
                messageByEmail.getContent()
        );
    }

    public static Message fromMessageByMagicNumber(MessageByMagicNumber messageByMagicNumber) {
        return new Message(
                messageByMagicNumber.getId(),
                messageByMagicNumber.getMagicNumber(),
                messageByMagicNumber.getEmail(),
                messageByMagicNumber.getTitle(),
                messageByMagicNumber.getContent()
        );
    }


    public UUID getId() {
        return id;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}