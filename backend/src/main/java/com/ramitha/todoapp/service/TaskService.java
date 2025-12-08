package com.ramitha.todoapp.service;

import com.ramitha.todoapp.dto.TaskRequest;
import com.ramitha.todoapp.dto.TaskResponse;
import com.ramitha.todoapp.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    TaskResponse getTask(Long id);

    List<TaskResponse> getAllTasks(TaskStatus status);

    TaskResponse updateTask(Long id, TaskRequest request);

    void deleteTask(Long id);
}