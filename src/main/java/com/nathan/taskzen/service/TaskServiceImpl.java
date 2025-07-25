package com.nathan.taskzen.service;

import com.nathan.taskzen.dto.TaskRequestDto;
import com.nathan.taskzen.dto.TaskResponseDto;
import com.nathan.taskzen.model.TaskEntity;
import com.nathan.taskzen.exception.TaskNotFoundException;
import com.nathan.taskzen.mapper.TaskMapper;
import com.nathan.taskzen.repo.TaskRepo;
// ✅ CORRECT support readOnly = true
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo repo;
    // 🟢 No need to autowire mapper if using static methods (you already imported it statically)
    // @Autowired private TaskMapper taskMapper;
    //if not use autowire
    @Autowired
    private TaskMapper taskMapper;

    public Optional<TaskResponseDto> createTask(TaskRequestDto taskRequestDto) {

        // Convertion from taskdto object to task entity object ->  mapToEntity(taskResponseDto)
        // TaskMapper::mapToEntity(taskResponseDto); // we can write like this using static
        // {} -> indicates the placeholder

        log.info("Inside createTask()"); // Removed since we are using AOP for logging Method call
        log.info("Creating task with title: {}", taskRequestDto.getTitle());
        TaskEntity taskEntity = taskMapper.mapToEntity(taskRequestDto);
        log.debug("Mapped TaskEntity: {}", taskEntity);
        TaskEntity saved = repo.save(taskEntity);
        log.debug("Saved task: {}", saved);
        log.info("Task saved successfully with ID: {}", taskEntity.getId());
        return Optional.of(taskMapper.mapToDto(saved));
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getAllTask() {

        log.info("Fetching all tasks from DB");
        List<TaskEntity> result = repo.findAll();
        List<TaskResponseDto> tasksDtos = result.stream().map(taskMapper::mapToDto).collect(Collectors.toList());

        // we can also wirte like below as well
       /* List<TaskResponseDto> tasksDtos = result.stream().map(task -> {
            return mapToDto(task);
        }).collect(Collectors.toList());*/

        //  What's happening?
        //taskMapper::mapToDto → is a method reference, meaning “use this method on each element of the stream”.
        //
        //taskMapper.mapToDto() → would execute immediately, which is not what you want in .map().
        log.debug("Fetched Tasks list -> {}", tasksDtos);

        return tasksDtos;
    }

    @Transactional(readOnly = true)
    public TaskResponseDto getTask(Long id) throws TaskNotFoundException {
        log.info("Fetching Task Requested by Id {}", id);
        TaskEntity taskEntity = repo.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.mapToDto(taskEntity);
    }

    public Optional<TaskResponseDto> updateTask(TaskResponseDto taskResponseDto) {
        log.info("Fetching Task Requested by Id {}", taskResponseDto.getId());
        TaskEntity taskEntity = repo.findById(taskResponseDto.getId()).orElseThrow(() -> new TaskNotFoundException(taskResponseDto.getId()));
        // Let MapStruct handle the update
        log.debug("Updating Task {} with {}", taskEntity, taskResponseDto);
        taskMapper.updateTaskFromDto(taskResponseDto, taskEntity);
        log.info("Updated Task");
        // No need to call repo.save(taskEntity); because @Transactional handles dirty checking
        //    repo.save(taskEntity);
        return Optional.ofNullable(taskMapper.mapToDto(taskEntity));
    }

    public Optional<String> deleteTask(Long id) throws TaskNotFoundException {
        log.info("Checking Task with Id = {} is exsist or not", id);
        if (!repo.existsById(id)) {
            throw new TaskNotFoundException(id);
        } else {
            log.info(" Deleting Task with Id = {} Deleting", id);
            repo.deleteById(id);
            return Optional.of("Deleted Successfully");

        }

    }

    public Optional<TaskResponseDto> updateStatus(Long id, Status status) {
        log.info("Fetching Task Requested by Id {}", id);
        TaskEntity taskEntity = repo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskEntity.setStatus(status); // Partial update
        log.info("Status updated to {}", status);
        //    repo.save(task); -> we can remove this since @Transactinal is used.
        return Optional.of(taskMapper.mapToDto(taskEntity));
    }

}
