package com.nathan.taskzen.controller;

import com.nathan.taskzen.enums.Status;
import com.nathan.taskzen.dto.TaskDto;
import com.nathan.taskzen.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "Task Management V2", description = "APIs for creating, retrieving, updating and deleting tasks")
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Bad Request / Validation error", content = @Content),
        @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Create a Task")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @PostMapping("/taskDto")
    public ResponseEntity<Optional<TaskDto>> createTask(@RequestBody @Valid TaskDto taskDto) {
        log.info("Creating task with title: {}", taskDto.getTitle());

        /* ResposnseEntity Constructor Style — Classic way of responseEntity
🔹 Traditional way using the constructor of ResponseEntity.
🔹 Slightly more verbose and less readable when you're chaining headers or other custom response setup.*/
        return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Tasks")
    @ApiResponse(responseCode = "200", description = "Fetched all tasks successfully")
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getAllTask() {
        log.info("Fetching all tasks");

/* ResposnseEntity Shorthand Style — Using ResponseEntity.status().body()
🔹 Fluent API Style — more readable, more modern.
🔹 Good when you want to build up a ResponseEntity with a specific status and body.
🔹 Introduced to make chained code more elegant, and we can also easly pass the custome header as well
Bonus: Add custom header with fluent style
return ResponseEntity.status(HttpStatus.CREATED)
                     .header("X-Custom-Header", "TaskCreated")
                     .body(taskService.createTask(task));
*/
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTask());
    }

    @Operation(summary = "Get a Task by Id using RequestParam")
    @ApiResponse(responseCode = "200", description = "Fetched Task by id successfully")
    @GetMapping("/task/taskid")
    public ResponseEntity<TaskDto> getTaskById(@RequestParam Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTask(id));

    }

    @Operation(summary = "Get a Task by Id using PathVariable")
    @ApiResponse(responseCode = "200", description = "Fetched Task by id successfully")
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTask(id));

    }

    @Operation(summary = "Update a Task")
    @PutMapping("/task/")
    @ApiResponse(responseCode = "202", description = "Task updated successfully")
    public ResponseEntity<Optional<TaskDto>> updateTask(@RequestBody @Valid TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTask(taskDto), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Update Status of a Task")
    @PatchMapping("/task/{id}/status")
    @ApiResponse(responseCode = "202", description = "Task Status updated successfully")
    public ResponseEntity<Optional<TaskDto>> updateStatus(@PathVariable Long id, @RequestParam Status status) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.updateStatus(id, status));
    }


    @Operation(summary = "Delete a Task")
    @DeleteMapping("/task/{id}")
    public ResponseEntity<Optional<String>> deleteTask(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.deleteTask(id));
    }
    

}
