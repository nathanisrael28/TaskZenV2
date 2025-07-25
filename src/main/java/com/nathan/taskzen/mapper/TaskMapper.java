package com.nathan.taskzen.mapper;

import com.nathan.taskzen.dto.TaskRequestDto;
import com.nathan.taskzen.dto.TaskResponseDto;
import com.nathan.taskzen.model.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

//Here wea are using MapStruct dependency to avoid manully setting and use of builder
@Mapper(componentModel = "spring")

public interface TaskMapper {
    // Create/Update: map from request DTO to Entity
    //  @Mapping(source = "id", target = "id", ignore = true)
    // @Mapping(source = "status", target = "status", expression = "java(\"PENDING\")")
    // default status
    TaskEntity mapToEntity(TaskRequestDto dto);

    // Map Entity to response DTO
   /* @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "dueDate", target = "dueDate")
    @Mapping(source = "status", target = "status")*/
    TaskResponseDto mapToDto(TaskEntity entity);

    // NEW: Update entity fields from DTO (only non-null ones)
    //  @Mapping(source = "id", target = "id") // optional if field names match
    //@MappingTarget tells MapStruct: “Don’t create new entity, just update this one.”
    void updateTaskFromDto(TaskResponseDto dto, @MappingTarget TaskEntity entity);


}
