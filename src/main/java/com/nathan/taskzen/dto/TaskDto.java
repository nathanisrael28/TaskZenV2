package com.nathan.taskzen.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.nathan.taskzen.enums.Status;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

// We can call DTO( Data transfer Object) as Bean/BussinessBean and Model also
@Schema(description = "Data transfer object for Task")
@Data
@ToString
public class TaskDto {


    @Schema(description = "Unique ID of the task", example = "101")
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters")
    @Schema(description = "Title of the task", example = "Complete Swagger docs")
    private String title;

    @Schema(description = "Description of the task", example = "Add API annotations for better documentation")
    private String description;

    @FutureOrPresent(message = "Due Date Can't be in Past Date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Due date in dd-MM-yyyy format", example = "01-07-2025")
    private LocalDate dueDate;

    @Schema(description = "Status of the task", example = "PENDING", allowableValues = {"PENDING", "COMPLETED"})
    @Enumerated(EnumType.STRING)
    private Status status;


    public TaskDto() {
    }


    private TaskDto(Builder builder) {

        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.dueDate = builder.dueDate;
        this.status = builder.status;

    }

    public static class Builder {

        private Long id;
        private String title;
        private String description;
        private LocalDate dueDate;
        private Status status;

        //write the parameter construtors for all the varibles
        // this is gives feasability to set the all the fields using builder
        // instead of send them at a go in one constrtrot and also
        // use instead of getters and setters

        public TaskDto.Builder id(Long id) {
            this.id = id;
            return this;
        }

        public TaskDto.Builder title(String title) {
            this.title = title;
            return this;
        }

        public TaskDto.Builder description(String description) {
            this.description = description;
            return this;
        }

        public TaskDto.Builder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public TaskDto.Builder status(Status status) {
            this.status = status;
            return this;
        }

        public TaskDto build() {

            return new TaskDto(this);
        }
    }

}


