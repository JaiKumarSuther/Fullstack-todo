package com.suthar.todo.controller;

import com.suthar.todo.entity.Entry;
import com.suthar.todo.service.TodoEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoEntryController {

    @Autowired
    TodoEntryService todoEntryService;

    @GetMapping
    public List<Entry> getTodos() {
        return todoEntryService.getTodos();
    }
//    @GetMapping
//    public ResponseEntity<?> getTodos() {
//        List<Entry> allTodos = todoEntryService.getTodos();
//        if(allTodos != null && !allTodos.isEmpty()) {
//            return new ResponseEntity<>(allTodos, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @PostMapping
//    public Entry saveTask(@RequestBody Entry entry) {
//        todoEntryService.saveTask(entry);
//        return entry;
//    }


    long id = 1;
    @PostMapping
    public ResponseEntity<Entry> saveTask(@RequestBody Entry entry) {
        try {
            entry.setId(id++);
            todoEntryService.saveTask(entry);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/{id}")
//    public Entry getTodoById(@PathVariable Long id) {
//        return todoEntryService.getById(id).orElse(null);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Entry> getTodoById(@PathVariable Long id) {
        Optional<Entry> todoEntry = todoEntryService.getById(id);
        if(todoEntry.isPresent()) {
            return new ResponseEntity<>(todoEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @DeleteMapping("/{id}")
//    public boolean deleteTodoById(@PathVariable ObjectId id) {
//        todoEntryService.deleteTodoById(id);
//        return true;
//    }
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteTodoById(@PathVariable Long id) {
    boolean deleted = todoEntryService.deleteTodoById(id);
    if (deleted) {
        return new ResponseEntity<>("Found and deleted",HttpStatus.NO_CONTENT);  // 204 No Content for successful deletion
    } else {
        return new ResponseEntity<>("Todo with ID " + id + " not found.", HttpStatus.NOT_FOUND);  // 404 Not Found with message
    }
}


//    @PutMapping("/{id}")
//    public Entry updateTodoById(@RequestBody Entry newEntry, @PathVariable ObjectId id) {
//        Entry old = todoEntryService.getById(id).orElse(null);
//        if(old != null) {
//            old.setTask(newEntry.getTask() != null && !newEntry.getTask().isEmpty() ? newEntry.getTask() : old.getTask());
//        }
//        todoEntryService.saveTask(old);
//        return old;
//    }


    @PutMapping("/{id}")
    public ResponseEntity<Entry> updateTodoById(@RequestBody Entry newEntry, @PathVariable Long id) {
        Optional<Entry> oldEntryOptional = todoEntryService.getById(id);
        if(oldEntryOptional.isPresent()) {

            Entry oldEntry = oldEntryOptional.get();
            long myid = oldEntry.getId();
            oldEntry.setTask(newEntry.getTask() != null && !newEntry.getTask().isEmpty() ? newEntry.getTask() : oldEntry.getTask());
            todoEntryService.saveTask(oldEntry);
            oldEntry.setId(myid);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
