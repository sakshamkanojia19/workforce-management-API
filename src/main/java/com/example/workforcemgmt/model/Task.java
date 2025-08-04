package com.example.workforcemgmt.model;

import lombok.Data;
import java.time.LocalDate;
import java.util.*;

@Data
public class Task {
    private Long id;
    private String title;
    private String description;
    private Staff assignee;
    private TaskStatus status = TaskStatus.ACTIVE;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Priority priority = Priority.MEDIUM;

    private List<Activity> activityHistory = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    @Data
    public static class Activity {
        private LocalDate timestamp = LocalDate.now();
        private String message;
    }

    @Data
    public static class Comment {
        private LocalDate timestamp = LocalDate.now();
        private String author;
        private String text;
    }

    public enum TaskStatus {
        ACTIVE, COMPLETED, CANCELLED
    }
}