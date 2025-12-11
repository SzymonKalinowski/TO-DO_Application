package com.example.crudapplication.service;

import com.example.crudapplication.dto.TaskDto;
import com.example.crudapplication.models.Status;
import com.example.crudapplication.models.Task;
import com.example.crudapplication.models.User;
import com.example.crudapplication.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public TaskDto create(TaskDto dto) {
        User user = userService.findById(dto.getUserId());
        Task task = new Task();
        task.setName(dto.getName());
        task.setUser(user);
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        if (dto.getDueDate() != null) {
            task.setDueDate(dto.getDueDate());
        }
        if (dto.getPriority() != null) {
            task.setPriority(dto.getPriority());
        }
        task = taskRepository.save(task);
        return toDto(task);
    }

    @Transactional(readOnly = true)
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Task> findByUser(Long userId) {
        User user = userService.findById(userId);
        return taskRepository.findByUser(user);
    }

    public TaskDto update(Long id, TaskDto dto) {
        Task task = findById(id);
        if (dto.getName() != null) {
            task.setName(dto.getName());
        }
        if (dto.getUserId() != null) {
            task.setUser(userService.findById(dto.getUserId()));
        }
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        if (dto.getDueDate() != null) {
            task.setDueDate(dto.getDueDate());
        }
        if (dto.getPriority() != null) {
            task.setPriority(dto.getPriority());
        }
        return toDto(taskRepository.save(task));
    }

    public TaskDto markCompleted(Long id) {
        Task task = findById(id);
        task.setStatus(Status.COMPLETED);
        return toDto(taskRepository.save(task));
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    public static TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setUserId(task.getUser().getId());
        dto.setStatus(task.getStatus());
        dto.setDueDate(task.getDueDate());
        dto.setPriority(task.getPriority());
        return dto;
    }
}
