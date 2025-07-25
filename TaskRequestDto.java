package com.nathan.taskzen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nathan.taskzen.enums.Status;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public class TaskRequestDto {

    private Long id;

    private String title;
    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dueDate;
    @Enumerated
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
