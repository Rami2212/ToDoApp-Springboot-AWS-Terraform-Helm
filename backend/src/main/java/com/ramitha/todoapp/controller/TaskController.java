package com.ramitha.todoapp.controller;

import com.ramitha.todoapp.dto.TaskRequest;
import com.ramitha.todoapp.dto.TaskResponse;
import com.ramitha.todoapp.entity.TaskStatus;
import com.ramitha.todoapp.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * Create a new task
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse created = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get all tasks, optionally filtered by status
     */
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @RequestParam(name = "status", required = false) TaskStatus status
    ) {
        List<TaskResponse> tasks = taskService.getAllTasks(status);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get a single task by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {
        TaskResponse task = taskService.getTask(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Update a task by id
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request
    ) {
        TaskResponse updated = taskService.updateTask(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a task by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
