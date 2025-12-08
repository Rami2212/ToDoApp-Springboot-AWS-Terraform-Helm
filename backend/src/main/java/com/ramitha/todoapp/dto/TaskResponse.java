package com.ramitha.todoapp.dto;

import com.ramitha.todoapp.entity.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
}