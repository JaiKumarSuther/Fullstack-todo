package com.suthar.todo.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document(collection = "todo_entries")
public class Entry {
    @Id
    Long id;
    String task;
    LocalDateTime date;
}
