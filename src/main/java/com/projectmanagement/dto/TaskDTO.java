package com.projectmanagement.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;  // LOW, MEDIUM, HIGH
    private String status;    // TODO, IN_PROGRESS, COMPLETED
    private String assignedTo;
    private Long projectId;
}