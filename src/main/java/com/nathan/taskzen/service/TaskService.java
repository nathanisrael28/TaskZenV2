package com.nathan.taskzen.service;

import com.nathan.taskzen.enums.Status;
import com.nathan.taskzen.dto.TaskDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    //Upgraded to Optional
//    String createTask(TaskDto taskDto);
//    List<TaskDto> getAllTask();
//    TaskDto getTask(Long id);
//    String updateTask(TaskDto taskDto);
//    String deleteTask(Long id);
//    String updateStatus(Long id, Status status);

    Optional<TaskDto> createTask(TaskDto taskDto);

    List<TaskDto> getAllTask();

    TaskDto getTask(Long id);

    Optional<TaskDto> updateTask(TaskDto taskDto);

    Optional<TaskDto> updateStatus(Long id, Status status);

    Optional<String> deleteTask(Long id);
}
