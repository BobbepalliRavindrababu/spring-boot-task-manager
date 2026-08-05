package com.ravindra.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskRequestDTO {
    @NotBlank(message="title cannot be blank")
    private String title;

    @NotBlank(message="descrition cannot be blank")
    private String description;

    @NotBlank(message = "staetus cannot b blank")
    private String status;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
