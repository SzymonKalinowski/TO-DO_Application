package com.example.crudapplication.controllers;

import com.example.crudapplication.dto.UserDto;
import com.example.crudapplication.models.User;
import com.example.crudapplication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto dto) {
        return userService.create(dto);
    }

    @GetMapping
    public List<UserDto> list() {
        List<User> all = userService.findAll();
        return all.stream().map(UserService::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return UserService.toDto(userService.findById(id));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @Valid @RequestBody UserDto dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
