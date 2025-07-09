package com.nathan.taskzen.mapper;

import com.nathan.taskzen.entity.TaskEntity;
import com.nathan.taskzen.model.TaskDto;

public class TaskMapper {

    public static TaskEntity mapToEntity(TaskDto taskDto){
        // Here we use builder class to Builder Pattern is a creational design pattern
        // that lets you build complex objects step-by-step, instead of using
        // big constructors with too many parameters.

      //  return TaskEntity.builder() sicne we are not using lobok so we have to use below syntax
        return new TaskEntity.Builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .dueDate(taskDto.getDueDate())
                .status(taskDto.getStatus())
                .build();
    }

    public static TaskDto mapToEntity(TaskEntity taskEntity){
        // Here we use builder class to Builder Pattern is a creational design pattern
        // that lets you build complex objects step-by-step, instead of using
        // big constructors with too many parameters.

        //  return TaskEntity.builder() sicne we are not using lobok so we have to use below syntax
        return new TaskDto.Builder()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .dueDate(taskEntity.getDueDate())
                .status(taskEntity.getStatus())
                .build();
    }

}
