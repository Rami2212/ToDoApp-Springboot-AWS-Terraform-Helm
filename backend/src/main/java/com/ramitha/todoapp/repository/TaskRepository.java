package com.ramitha.todoapp.repository;

import com.ramitha.todoapp.entity.Task;
import com.ramitha.todoapp.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);
}
