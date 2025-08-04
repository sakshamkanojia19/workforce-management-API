package com.example.workforcemgmt.dto;

import com.example.workforcemgmt.model.Task;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Long assigneeId;
    private String assigneeName;
    private Task.TaskStatus status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Priority priority;

    private List<Task.Activity> activityHistory;
    private List<Task.Comment> comments;

    public enum Priority { LOW, MEDIUM, HIGH }
}