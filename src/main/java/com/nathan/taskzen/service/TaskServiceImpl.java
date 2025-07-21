package com.nathan.taskzen.service;

import com.nathan.taskzen.model.TaskEntity;
import com.nathan.taskzen.dto.TaskDto;
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

import static com.nathan.taskzen.mapper.TaskMapper.mapToDto;
import static com.nathan.taskzen.mapper.TaskMapper.mapToEntity;

@Service
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo repo;
    // 🟢 No need to autowire mapper if using static methods (you already imported it statically)
    // @Autowired private TaskMapper taskMapper;

    public Optional<TaskDto> createTask(TaskDto taskDto) {

        // Convertion from taskdto object to task entity object ->  mapToEntity(taskDto)
        // TaskMapper::mapToEntity(taskDto); // we can write like this using static
        // {} -> indicates the placeholder
        log.info("Creating task with title: {}", taskDto.getTitle());
        TaskEntity taskEntity = mapToEntity(taskDto);

/*        // Default status handling using Optional
        if (taskEntity.getStatus() == null) {
            taskEntity.setStatus(Status.PENDING);
        }*/

        TaskEntity saved = repo.save(taskEntity);
        return Optional.of(mapToDto(saved));
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllTask() {


        List<TaskEntity> result = repo.findAll();
        List<TaskDto> tasksDtos = result.stream().map(TaskMapper::mapToDto).collect(Collectors.toList());
        // we can also wirte like below as well
       /* List<TaskDto> tasksDtos = result.stream().map(task -> {
            return mapToDto(task);
        }).collect(Collectors.toList());*/

        return tasksDtos;
    }

    @Transactional(readOnly = true)
    public TaskDto getTask(Long id) throws TaskNotFoundException {

        TaskEntity taskEntity = repo.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return mapToDto(taskEntity);
    }

    public Optional<TaskDto> updateTask(TaskDto taskDto) {

        TaskEntity taskEntity = repo.findById(taskDto.getId()).orElseThrow(() -> new TaskNotFoundException(taskDto.getId()));
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setDueDate(taskDto.getDueDate());
        taskEntity.setStatus(taskDto.getStatus());
        //    repo.save(taskEntity); //-> we can remove this since @Transactional is used.
        return Optional.ofNullable(mapToDto(taskEntity));
    }

    public Optional<String> deleteTask(Long id) {

        if (!repo.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repo.deleteById(id);
        return Optional.of("Deleted Successfully");
    }

    public Optional<TaskDto> updateStatus(Long id, Status status) {
        TaskEntity taskEntity = repo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskEntity.setStatus(status); // Partial update
        //    repo.save(task); -> we can remove this since @Transactinal is used.
        return Optional.of(mapToDto(taskEntity));
    }

}
