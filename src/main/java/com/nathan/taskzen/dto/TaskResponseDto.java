package com.nathan.taskzen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nathan.taskzen.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;


import java.time.LocalDate;

// We can call DTO( Data transfer Object) as Bean/BussinessBean and Model also
@Schema(description = "Data transfer object for Task")
public class TaskResponseDto {


    @Schema(description = "Unique ID of the task", example = "101")
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters")
    @Schema(description = "Title of the task", example = "Complete Swagger docs")
    private String title;

    @Schema(description = "Description of the task", example = "Add API annotations for better documentation")
    private String description;

    //@FutureOrPresent(message = "Due Date Can't be in Past Date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Due date in dd-MM-yyyy format", example = "01-07-2025")
    private LocalDate dueDate;

    @Schema(description = "Status of the task", example = "PENDING", allowableValues = {"PENDING", "COMPLETED"})
    @Enumerated(EnumType.STRING)
    private Status status;

    public TaskResponseDto() {
    }

    public TaskResponseDto(Long id, String title, String description, LocalDate dueDate, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
