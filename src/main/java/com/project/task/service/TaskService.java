package com.project.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.task.entity.Task;
import com.project.task.repository.TaskRepository;

@Service
public class TaskService {
    @Autowired private TaskRepository repo;
    public Task create(Task t) { return repo.save(t); }
    public List<Task> listAll() { return repo.findAll(); }
    public Task get(Long id) { return repo.findById(id).orElseThrow(); }
    public Task update(Long id, Task t) {
        Task exist = get(id);
        exist.setTitle(t.getTitle()); exist.setDescription(t.getDescription());
        exist.setStatus(t.getStatus());
        return repo.save(exist);
    }
    public void delete(Long id) { repo.deleteById(id); }
    public List<Task> filterByStatus(Task.Status status) { return repo.findByStatus(status); }
}