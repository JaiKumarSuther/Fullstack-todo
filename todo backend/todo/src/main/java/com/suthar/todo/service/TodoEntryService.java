package com.suthar.todo.service;

import com.suthar.todo.entity.Entry;
import com.suthar.todo.repository.TodoEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TodoEntryService {

    @Autowired
    TodoEntryRepository todoEntryRepository;
//    long id = 1;

    public void saveTask(Entry entry) {
//        entry.setId((long)id++);
        entry.setDate(LocalDateTime.now());
        todoEntryRepository.save(entry);
    }

    public List<Entry> getTodos() {
        return todoEntryRepository.findAll();
    }

    public Optional<Entry> getById(Long id) {
        return todoEntryRepository.findById(id);
    }

    public boolean deleteTodoById(Long id) {
        todoEntryRepository.deleteById(id);
        return true;
    }

}
