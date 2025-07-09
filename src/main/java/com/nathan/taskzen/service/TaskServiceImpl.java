package com.nathan.taskzen.service;

import com.nathan.taskzen.entity.TaskEntity;
import com.nathan.taskzen.exception.TaskNotFoundException;
import com.nathan.taskzen.model.TaskDto;
import com.nathan.taskzen.repo.TaskRepo;
// ✅ CORRECT support readOnly = true
import org.springframework.transaction.annotation.Transactional;
// ❌ WRONG - from jakarta.transaction does NOT support readOnly = true
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nathan.taskzen.enums.Status;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepo repo;

    public Optional<TaskDto> createTask(TaskDto taskDto){

        // Convertion from taskdto object to task entity object
        TaskEntity task =new TaskEntity();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        if (taskDto.getStatus() == null) {
            task.setStatus(Status.PENDING);
        } else {
            task.setStatus(taskDto.getStatus());
        }
        repo.save(task);

        // optionally we can verify whether the Task is added in DB or not. and return the task again.
        // but again it requried convertion of entity to Dto
         // repo.findById(taskDto.getId());


        return repo.save(task);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllTask(){

        List<TaskEntity> result = repo.findAll();

       List<TaskDto> tasks = result.stream().map(task -> {
           TaskDto tasksdto =new TaskDto();
           tasksdto.setId(task.getId());
           tasksdto.setTitle(task.getTitle());
           tasksdto.setDescription(task.getDescription());
           tasksdto.setDueDate(task.getDueDate());
           tasksdto.setStatus(task.getStatus());
           return tasksdto;

       }).collect(Collectors.toList());
        return tasks;
    }

    @Transactional(readOnly = true)
    public TaskDto getTask(Long id) throws TaskNotFoundException{

        TaskEntity taskEntity = repo.findById(id).orElseThrow(()-> new TaskNotFoundException(id));

        TaskDto taskDto = new TaskDto();
        taskDto.setId(taskEntity.getId());
        taskDto.setTitle(taskEntity.getTitle());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setDueDate(taskEntity.getDueDate());
        taskDto.setStatus(taskEntity.getStatus());

        return taskDto;
    }

    public String updateTask(TaskDto taskDto){

        TaskEntity taskEntity = repo.findById(taskDto.getId()).orElseThrow(()-> new TaskNotFoundException(taskDto.getId()));
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setDueDate(taskDto.getDueDate());
        taskEntity.setStatus(taskDto.getStatus()); // Business logic enforced here
       // repo.save(task); -> we can remove this since @Transactional is used.
        return "Updated";
    }

    public String  deleteTask(Long id){

        repo.deleteById(id);

        return "Task " + id +" is Deleted";
    }

    public String updateStatus(Long id, Status status) {
        TaskEntity task = repo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setStatus(status); // Partial update
    //    repo.save(task); -> we can remove this since @Transactinal is used.
        return "Status Updated";
    }

}
