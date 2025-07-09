package com.nathan.taskzen.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.nathan.taskzen.enums.Status;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

// We can call DTO( Data transfer Object) as Bean/BussinessBean and Model also
@Schema(description = "Data transfer object for Task")
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
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Due date in dd-MM-yyyy format", example = "01-07-2025")
    private LocalDate dueDate;

    @Schema(description = "Status of the task", example = "PENDING")
    @Enumerated(EnumType.STRING)
    private Status status;


    public TaskDto() {
    }

    public TaskDto(Long id, String title, String description, LocalDate dueDate, Status status) {
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

    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", status=" + status +
                '}';
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

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public TaskDto build() {
            return new TaskDto(this);
        }
    }


}


