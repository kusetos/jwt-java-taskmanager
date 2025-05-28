package com.project.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Task.Status status);
}