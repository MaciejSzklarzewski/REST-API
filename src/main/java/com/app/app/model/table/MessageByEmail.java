package com.app.app.model.table;

import com.app.app.model.Message;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Table("messages_by_email")
public class MessageByEmail {

    @PrimaryKeyColumn(type = PARTITIONED)
    private String email;

    @PrimaryKeyColumn
    private UUID id;

    private int magicNumber;
    private String title;
    private String content;


    public MessageByEmail(String email, UUID id, int magicNumber, String title, String content) {
        this.email = email;
        this.id = id;
        this.magicNumber = magicNumber;
        this.title = title;
        this.content = content;
    }

    public static MessageByEmail fromMessage(Message message) {
        return new MessageByEmail(
                message.getEmail(),
                message.getId(),
                message.getMagicNumber(),
                message.getTitle(),
                message.getContent()
        );
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
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