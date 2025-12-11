package com.example.crudapplication.dto;

import com.example.crudapplication.models.Priority;
import com.example.crudapplication.models.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

public @Data class TaskDto {
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Long userId;

    private Status status;

    @FutureOrPresent
    private LocalDate dueDate;

    private Priority priority;
}
