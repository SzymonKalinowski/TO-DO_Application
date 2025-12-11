package com.example.crudapplication.controllers;

import com.example.crudapplication.dto.TaskDto;
import com.example.crudapplication.models.Status;
import com.example.crudapplication.models.Task;
import com.example.crudapplication.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@Valid @RequestBody TaskDto dto) {
        return taskService.create(dto);
    }

    @GetMapping
    public List<TaskDto> list(@RequestParam(value = "userId", required = false) Long userId,
                              @RequestParam(value = "status", required = false) String status) {
        List<Task> tasks;
        if (userId != null && status != null) {
            Status s = parseStatus(status);
            tasks = taskService.findByUser(userId).stream()
                    .filter(t -> t.getStatus() == s)
                    .collect(Collectors.toList());
        } else if (userId != null) {
            tasks = taskService.findByUser(userId);
        } else {
            tasks = taskService.findAll();
            if (status != null) {
                Status s = parseStatus(status);
                tasks = tasks.stream().filter(t -> t.getStatus() == s).collect(Collectors.toList());
            }
        }
        return tasks.stream().map(TaskService::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskDto get(@PathVariable Long id) {
        return TaskService.toDto(taskService.findById(id));
    }

    @PutMapping("/{id}")
    public TaskDto update(@PathVariable Long id, @Valid @RequestBody TaskDto dto) {
        return taskService.update(id, dto);
    }

    @PatchMapping("/{id}/complete")
    public TaskDto markComplete(@PathVariable Long id) {
        return taskService.markCompleted(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { taskService.delete(id); }

    private Status parseStatus(String status) {
        String s = status.trim().toLowerCase(Locale.ROOT);
        return switch (s) {
            case "pending" -> Status.PENDING;
            case "in_progress"-> Status.IN_PROGRESS;
            case "completed" -> Status.COMPLETED;
            default -> throw new IllegalArgumentException("Unknown status");
        };
    }
}
