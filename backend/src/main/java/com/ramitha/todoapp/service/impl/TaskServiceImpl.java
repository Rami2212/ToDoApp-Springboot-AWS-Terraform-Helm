package com.ramitha.todoapp.service.impl;

import com.ramitha.todoapp.dto.TaskRequest;
import com.ramitha.todoapp.dto.TaskResponse;
import com.ramitha.todoapp.entity.Task;
import com.ramitha.todoapp.entity.TaskStatus;
import com.ramitha.todoapp.exception.ResourceNotFoundException;
import com.ramitha.todoapp.repository.TaskRepository;
import com.ramitha.todoapp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(TaskRequest request) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.PENDING)
                .build();

        Task saved = taskRepository.save(task);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTask(Long id) {
        Task task = findTaskById(id);
        return mapToResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks(TaskStatus status) {
        List<Task> tasks = (status == null)
                ? taskRepository.findAll()
                : taskRepository.findByStatus(status);

        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task existing = findTaskById(id);

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }

        Task updated = taskRepository.save(existing);
        return mapToResponse(updated);
    }

    @Override
    public void deleteTask(Long id) {
        Task existing = findTaskById(id);
        taskRepository.delete(existing);
    }

    private Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }
}
