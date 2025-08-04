package com.example.workforcemgmt.controller;

import com.example.workforcemgmt.dto.*;
import com.example.workforcemgmt.model.Priority;
import com.example.workforcemgmt.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping
    public TaskDto create(@RequestBody CreateTaskRequest req) {
        return service.createTask(req);
    }

    @PutMapping("/{id}/assign/{staffId}")
    public TaskDto assign(@PathVariable Long id, @PathVariable Long staffId) {
        return service.assignTaskToStaff(id, staffId);
    }

    @GetMapping("/staff/{staffId}")
    public List<TaskDto> getByStaff(
            @PathVariable Long staffId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return service.getTasksForStaff(staffId, from, to);
    }

    @GetMapping("/{id}")
    public TaskDto getOne(@PathVariable Long id) {
        return service.getTask(id);
    }

    /* priority endpoints */
    @PutMapping("/{id}/priority")
    public TaskDto changePriority(@PathVariable Long id, @RequestBody ChangePriorityRequest req) {
        return service.changePriority(id, req);
    }

    @GetMapping("/priority/{priority}")
    public List<TaskDto> byPriority(@PathVariable Priority priority) {
        return service.getTasksByPriority(priority);
    }

    /* comments */
    @PostMapping("/{id}/comments")
    public TaskDto comment(@PathVariable Long id, @RequestBody CommentRequest req) {
        return service.addComment(id, req);
    }
}