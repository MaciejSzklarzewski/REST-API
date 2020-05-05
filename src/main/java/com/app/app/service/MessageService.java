package com.app.app.service;

import com.app.app.model.Message;
import com.app.app.model.table.MessageByEmail;
import com.app.app.model.table.MessageByMagicNumber;
import com.app.app.repository.MessageByEmailRepository;
import com.app.app.repository.MessageByMagicNumberRepository;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private static final int TTL = 300;

    private final MessageByEmailRepository messageByEmailRepository;
    private final MessageByMagicNumberRepository messageByMagicNumberRepository;


    @Inject
    public MessageService(MessageByEmailRepository messageByEmailRepository, MessageByMagicNumberRepository messageByMagicNumberRepository) {
        this.messageByEmailRepository = messageByEmailRepository;
        this.messageByMagicNumberRepository = messageByMagicNumberRepository;
    }


    public Message saveMessage(Message message) {
        MessageByEmail messageByEmail = MessageByEmail.fromMessage(message);
        MessageByMagicNumber messageByMagicNumber = MessageByMagicNumber.fromMessage(message);

        messageByEmailRepository.save(messageByEmail, TTL);
        messageByMagicNumberRepository.save(messageByMagicNumber, TTL);

        return message;
    }

    public void deleteMessages(List<Message> messages) {
        messages.forEach(message -> {
            messageByEmailRepository.deleteByEmailAndId(message.getEmail(), message.getId());
            messageByMagicNumberRepository.deleteByMagicNumber(message.getMagicNumber());
        });
    }

    public List<Message> getMessagesByEmail(String email, int requestedPage, int size) {
        CassandraPageRequest pageRequest = CassandraPageRequest.first(size);
        Slice<MessageByEmail> messagesByEmail = messageByEmailRepository.findByEmail(email, pageRequest);

        for (int i=0; i<requestedPage && messagesByEmail.hasNext(); i++) {
            messagesByEmail = messageByEmailRepository.findByEmail(email, messagesByEmail.nextPageable());
        }

        return messagesByEmail.stream()
                .map(Message::fromMessageByEmail)
                .collect(Collectors.toList());
    }

    public List<Message> getMessagesByMagicNumber(int magicNumber) {
        List<MessageByMagicNumber> messagesByMagicNumber = messageByMagicNumberRepository.findByMagicNumber(magicNumber);

        return messagesByMagicNumber.stream()
                .map(Message::fromMessageByMagicNumber)
                .collect(Collectors.toList());
    }
}