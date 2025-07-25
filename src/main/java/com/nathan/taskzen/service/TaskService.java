package com.nathan.taskzen.service;

import com.nathan.taskzen.dto.TaskRequestDto;
import com.nathan.taskzen.dto.TaskResponseDto;
import com.nathan.taskzen.enums.Status;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    //Upgraded to Optional
//    String createTask(TaskResponseDto taskDto);
//    List<TaskResponseDto> getAllTask();
//    TaskResponseDto getTask(Long id);
//    String updateTask(TaskResponseDto taskDto);
//    String deleteTask(Long id);
//    String updateStatus(Long id, Status status);

    Optional<TaskResponseDto> createTask(@Valid TaskRequestDto taskResponseDto);

    List<TaskResponseDto> getAllTask();

    TaskResponseDto getTask(Long id);

    Optional<TaskResponseDto> updateTask(TaskResponseDto taskResponseDto);

    Optional<TaskResponseDto> updateStatus(Long id, Status status);

    Optional<String> deleteTask(Long id);
}
