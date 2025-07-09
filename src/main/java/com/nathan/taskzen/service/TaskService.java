package com.nathan.taskzen.service;

import com.nathan.taskzen.enums.Status;
import com.nathan.taskzen.model.TaskDto;

import java.util.List;

public interface TaskService {


    String createTask(TaskDto taskDto);
    List<TaskDto> getAllTask();
    TaskDto getTask(Long id);
    String updateTask(TaskDto taskDto);
    String deleteTask(Long id);
    String updateStatus(Long id, Status status);
}
