package com.nathan.taskzen.controller;

import com.nathan.taskzen.entity.TaskEntity;
import com.nathan.taskzen.enums.Status;
import com.nathan.taskzen.model.TaskDto;
import com.nathan.taskzen.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Task Management V2", description = "APIs for creating, retrieving, updating and deleting tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Create a Task")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @PostMapping("/taskDto")
    public ResponseEntity<String> createTask(@RequestBody @Valid TaskDto task) {
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Tasks")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fetched all tasks successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getAllTask() {
        return new ResponseEntity<>(taskService.getAllTask(), HttpStatus.OK);
    }

    @Operation(summary = "Get a Task by Id using RequestParam")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/task/taskid")
    public ResponseEntity<TaskDto> getTaskById(@RequestParam Long id) {
        return new ResponseEntity<>(taskService.getTask(id), HttpStatus.OK);
    }

    @Operation(summary = "Get a Task by Id using PathVariable")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTask(id), HttpStatus.OK);
    }

    @Operation(summary = "Update a Task")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/task/")
    public ResponseEntity<String> updateTask(@RequestBody @Valid TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTask(taskDto), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Update Status of a Task")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Task status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status or ID"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/task/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam Status status) {
        return new ResponseEntity<>(taskService.updateStatus(id, status), HttpStatus.ACCEPTED);
    }


    @Operation(summary = "Delete a Task")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.ACCEPTED);
    }



}
