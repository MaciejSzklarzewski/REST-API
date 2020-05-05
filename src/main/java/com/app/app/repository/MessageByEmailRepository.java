package com.app.app.repository;

import com.app.app.model.table.MessageByEmail;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface MessageByEmailRepository extends CassandraRepository<MessageByEmail, String>, SaveWithTtl<MessageByEmail> {

    Slice<MessageByEmail> findByEmail(String email, Pageable pageRequest);

    void deleteByEmailAndId(String email, UUID id);
}