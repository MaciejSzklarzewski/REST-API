package com.app.app.repository;

import com.app.app.model.table.MessageByMagicNumber;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface MessageByMagicNumberRepository extends CassandraRepository<MessageByMagicNumber, Integer>, SaveWithTtl<MessageByMagicNumber> {

    List<MessageByMagicNumber> findByMagicNumber(int magicNumber);

    void deleteByMagicNumber(int magicNumber);
}