package com.suthar.todo.repository;

import com.suthar.todo.entity.Entry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoEntryRepository extends MongoRepository<Entry, Long> {

}
