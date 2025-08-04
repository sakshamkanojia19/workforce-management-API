package com.example.workforcemgmt.service;

import com.example.workforcemgmt.dto.*;
import com.example.workforcemgmt.mapper.TaskMapper;
import com.example.workforcemgmt.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final Map<Long, Task> tasks = new HashMap<>();
    private final Map<Long, Staff> staffDb = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);
    private final TaskMapper mapper;

    /* bootstrap data */
    {
        staffDb.put(1L, new Staff(1L, "Alice"));
        staffDb.put(2L, new Staff(2L, "Bob"));
    }

    /* ------------ TASK CRUD ------------ */

    public TaskDto createTask(CreateTaskRequest req) {
        Staff assignee = staffDb.get(req.getAssigneeId());
        if (assignee == null) throw new IllegalArgumentException("Staff not found");

        Task t = new Task();
        t.setId(idGen.getAndIncrement());
        t.setTitle(req.getTitle());
        t.setDescription(req.getDescription());
        t.setAssignee(assignee);
        t.setStartDate(req.getStartDate());
        t.setDueDate(req.getDueDate());
        t.getActivityHistory().add(activity("Task created and assigned to " + assignee.getName()));

        tasks.put(t.getId(), t);
        return mapper.toDto(t);
    }

    public TaskDto assignTaskToStaff(Long taskId, Long newStaffId) {
        Task task = tasks.get(taskId);
        if (task == null) throw new NoSuchElementException("Task not found");

        /* cancel previous duplicate */
        tasks.values().stream()
                .filter(t -> t.getTitle().equals(task.getTitle()))
                .filter(t -> t.getStatus() == Task.TaskStatus.ACTIVE)
                .filter(t -> !t.getId().equals(taskId))
                .findFirst()
                .ifPresent(old -> {
                    old.setStatus(Task.TaskStatus.CANCELLED);
                    old.getActivityHistory().add(activity("Task reassigned (duplicate cancelled)"));
                });

        Staff newStaff = staffDb.get(newStaffId);
        if (newStaff == null) throw new IllegalArgumentException("Staff not found");
        task.setAssignee(newStaff);
        task.getActivityHistory().add(activity("Task reassigned to " + newStaff.getName()));
        return mapper.toDto(task);
    }

    public List<TaskDto> getTasksForStaff(Long staffId, LocalDate from, LocalDate to) {
        return tasks.values().stream()
                .filter(t -> t.getAssignee().getId().equals(staffId))
                .filter(t -> t.getStatus() != Task.TaskStatus.CANCELLED)
                .filter(t -> {
                    LocalDate start = t.getStartDate();
                    return (start.isEqual(from) || start.isAfter(from)) && start.isBefore(to.plusDays(1))
                            || (start.isBefore(from) && t.getStatus() == Task.TaskStatus.ACTIVE);
                })
                .sorted(Comparator.comparing(Task::getStartDate))
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskDto getTask(Long id) {
        Task t = tasks.get(id);
        if (t == null) throw new NoSuchElementException("Task not found");
        return mapper.toDto(t);
    }

    /* ------------ PRIORITY ------------ */

    public TaskDto changePriority(Long taskId, ChangePriorityRequest req) {
        Task t = tasks.get(taskId);
        if (t == null) throw new NoSuchElementException("Task not found");
        Priority old = t.getPriority();
        t.setPriority(req.getPriority());
        t.getActivityHistory().add(activity("Priority changed from " + old + " to " + req.getPriority()));
        return mapper.toDto(t);
    }

    public List<TaskDto> getTasksByPriority(Priority p) {
        return tasks.values().stream()
                .filter(t -> t.getPriority() == p)
                .map(mapper::toDto)
                .toList();
    }

    /* ------------ COMMENTS ------------ */

    public TaskDto addComment(Long taskId, CommentRequest req) {
        Task t = tasks.get(taskId);
        if (t == null) throw new NoSuchElementException("Task not found");
        Task.Comment c = new Task.Comment();
        c.setAuthor(req.getAuthor());
        c.setText(req.getText());
        t.getComments().add(c);
        t.getActivityHistory().add(activity("Comment added by " + req.getAuthor()));
        return mapper.toDto(t);
    }

    private Task.Activity activity(String msg) {
        Task.Activity act = new Task.Activity();
        act.setMessage(msg);
        return act;
    }
}