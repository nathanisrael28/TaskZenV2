package com.nathan.taskzen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nathan.taskzen.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Schema(description = "Data transfer object for Task")
public class TaskRequestDto {

    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters")
    @Schema(description = "Title of the task", example = "Complete Swagger docs")
    private String title;

    @Schema(description = "Description of the task", example = "Add API annotations for better documentation")
    private String description;

    @FutureOrPresent(message = "Due Date Can't be in Past Date") // removed while updating the task, its validating
    //
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Due date in dd-MM-yyyy format", example = "01-07-2025")
    private LocalDate dueDate;

    public TaskRequestDto() {
    }

    public TaskRequestDto(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public @NotBlank(message = "Title is required") @Size(min = 3, message = "Title must be at least 3 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title is required") @Size(min = 3, message = "Title must be at least 3 characters") String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @FutureOrPresent(message = "Due Date Can't be in Past Date") LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@FutureOrPresent(message = "Due Date Can't be in Past Date") LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
