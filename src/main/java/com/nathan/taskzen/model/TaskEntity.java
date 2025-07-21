package com.nathan.taskzen.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.nathan.taskzen.enums.Status;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@ToString
@Table(name = "tasks")
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

    // so here the values we map the values which we pass to builder calls build() method
    private TaskEntity(Builder builder) {
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

        public TaskEntity build() {

            return new TaskEntity(this);
        }

    }


}
