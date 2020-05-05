package com.app.app.repository;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.InsertOptions;

import javax.inject.Inject;

public class SaveWithTtlImpl<T> implements SaveWithTtl<T> {

    private final CassandraOperations cassandraOperations;


    @Inject
    public SaveWithTtlImpl(CassandraOperations cassandraOperations) {
        this.cassandraOperations = cassandraOperations;
    }


    @Override
    public T save(T entity, int ttl) {
        InsertOptions insertOptions = InsertOptions.builder().ttl(ttl).build();
        cassandraOperations.insert(entity, insertOptions);

        return entity;
    }
}