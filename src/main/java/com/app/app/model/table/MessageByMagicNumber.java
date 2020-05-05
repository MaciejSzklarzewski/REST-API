package com.app.app.model.table;

import com.app.app.model.Message;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Table("messages_by_magic_number")
public class MessageByMagicNumber {

    @PrimaryKeyColumn(type = PARTITIONED)
    private int magicNumber;

    @PrimaryKeyColumn
    private UUID id;

    private String email;
    private String title;
    private String content;


    public MessageByMagicNumber(int magicNumber, UUID id, String email, String title, String content) {
        this.magicNumber = magicNumber;
        this.id = id;
        this.email = email;
        this.title = title;
        this.content = content;
    }

    public static MessageByMagicNumber fromMessage(Message message) {
        return new MessageByMagicNumber(
                message.getMagicNumber(),
                message.getId(),
                message.getEmail(),
                message.getTitle(),
                message.getContent()
        );
    }


    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}