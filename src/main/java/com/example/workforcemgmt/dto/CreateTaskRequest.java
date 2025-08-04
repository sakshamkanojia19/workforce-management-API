package com.example.workforcemgmt.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private Long assigneeId;
    private LocalDate startDate;
    private LocalDate dueDate;
}