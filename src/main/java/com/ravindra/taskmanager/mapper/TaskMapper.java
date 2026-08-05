package com.ravindra.taskmanager.mapper;

import com.ravindra.taskmanager.dto.TaskRequestDTO;
import com.ravindra.taskmanager.dto.TaskResponseDTO;
import com.ravindra.taskmanager.entity.Task;

public class TaskMapper {

    public static Task toEntity(TaskRequestDTO dto){
        Task task=new Task();

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());

        return task;
    }

    public static TaskResponseDTO toResponseDto(Task task){

        TaskResponseDTO responseDTO=new TaskResponseDTO();

        responseDTO.setId(task.getId());
        responseDTO.setTitle(task.getTitle());
        responseDTO.setDescription(task.getDescription());
        responseDTO.setStatus(task.getStatus());
        responseDTO.setCreatedAt(task.getCreatedAt());

        return responseDTO;
    }
}
