package com.example.crudapplication.repository;

import com.example.crudapplication.models.Status;
import com.example.crudapplication.models.Task;
import com.example.crudapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByStatus(Status status);
    List<Task> findByUserAndStatus(User user, Status status);
}
