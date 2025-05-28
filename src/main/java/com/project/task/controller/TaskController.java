package com.project.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.task.entity.Task;
import com.project.task.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired private TaskService service;

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task t) {
        return ResponseEntity.ok(service.create(t));
    }

    @GetMapping
    public ResponseEntity<List<Task>> list(@RequestParam(required=false) Task.Status status) {
        return status == null ?
            ResponseEntity.ok(service.listAll()) :
            ResponseEntity.ok(service.filterByStatus(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task t) {
        return ResponseEntity.ok(service.update(id, t));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
