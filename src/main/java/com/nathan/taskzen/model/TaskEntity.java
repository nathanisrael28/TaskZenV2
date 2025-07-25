package com.nathan.taskzen.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.nathan.taskzen.enums.Status;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Data
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters")
    private String title;

    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public TaskEntity() {
    }

    public TaskEntity(Long id, String title, String description, LocalDate dueDate, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }


}
