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

import java.time.LocalDate;

// We can call DTO( Data transfer Object) as Bean/BussinessBean and Model also
@Schema(description = "Data transfer object for Task")
@Data
@ToString
public class TaskResponseDto {


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


}


