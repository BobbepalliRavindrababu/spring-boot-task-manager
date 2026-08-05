package com.ravindra.taskmanager.service;

import com.ravindra.taskmanager.dto.PagedResponse;
import com.ravindra.taskmanager.dto.TaskRequestDTO;
import com.ravindra.taskmanager.dto.TaskResponseDTO;
import com.ravindra.taskmanager.entity.Task;
import com.ravindra.taskmanager.enums.TaskSortField;
import com.ravindra.taskmanager.exception.TaskNotFoundException;
import com.ravindra.taskmanager.mapper.TaskMapper;
import com.ravindra.taskmanager.repository.TaskRepository;
import com.ravindra.taskmanager.specification.TaskSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.awt.print.Pageable;
import java.util.List;

@Service
public class TaskService {

    private static final Logger logger= LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public TaskResponseDTO saveTask(TaskRequestDTO requestDTO){

        logger.info("new Task is created with title:{}",requestDTO.getTitle());

        Task task= TaskMapper.toEntity(requestDTO);
        Task savedTask=taskRepository.save(task);
        logger.info("Task is created with the id:{}",savedTask.getId());
        return TaskMapper.toResponseDto(savedTask);

        //return taskRepository.save(task);
    }

//    public PagedResponse<TaskResponseDTO> getAllTasks(int page, int size, TaskSortField sortBy, Sort.Direction direction){
//
//        logger.info("fetching the tasks.Page:{}, Size:{}",page,size);
//
//        Sort sort=Sort.by(direction,sortBy.getFieldName());
//        Pageable pageable= PageRequest.of(page,size,sort);
//        Page<Task> taskPage=taskRepository.findAll(pageable);
//        List<TaskResponseDTO> taskDTO= taskPage.getContent().stream().map(TaskMapper::toResponseDto).toList();
//        return new PagedResponse<>(
//                taskDTO,
//                taskPage.getNumber(),
//                taskPage.getSize(),
//                taskPage.getTotalElements(),
//                taskPage.getTotalPages(),
//                taskPage.isLast()
//        );
//    }

    public PagedResponse<TaskResponseDTO> searchTasks(
            String title,
            String status,
            int page,
            int size,
            TaskSortField sortBy,
            Sort.Direction direction

    ){
        Specification<Task> spec = Specification.unrestricted();

        if(title!=null && !title.isBlank()){
            spec=spec.and(TaskSpecification.hasTitle(title));
        }

        if(status!=null && !status.isBlank()){
            spec=spec.and(TaskSpecification.hasStatus(status));
        }

        Sort sort=Sort.by(sortBy.getFieldName());

        sort=direction== Sort.Direction.ASC? sort.ascending(): sort.descending();

        Pageable pageable=PageRequest.of(page,size,sort);

        Page<Task> tasksPage=taskRepository.findAll(spec,pageable);

        List<TaskResponseDTO> tasks=tasksPage.getContent()
                .stream()
                .map(TaskMapper::toResponseDto)
                .toList();

        return new PagedResponse<>(
                tasks,
                tasksPage.getNumber(),
                tasksPage.getSize(),
                tasksPage.getTotalElements(),
                tasksPage.getTotalPages(),
                tasksPage.isLast()
        );
    }

    public TaskResponseDTO getById(Long id){
        logger.info("searching for the task with id:{}",id);
        Task task= taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException("Task not found with id:"+ id));
        logger.info("found the task with the id:{}",task.getId());
        return TaskMapper.toResponseDto(task);
    }

    public List<TaskResponseDTO> getTaskByStatus(String status){

        logger.info("fetching the tasks with the status:{}",status);

        List<Task> tasks=taskRepository.findByStatus(status);

        return tasks.stream()
                .map(TaskMapper::toResponseDto)
                .toList();
    }

    public List<TaskResponseDTO> searchTasksByTitle(String title){

        logger.info("Fetching the tasks using the title");

        List<Task> tasks=taskRepository.findByTitleContainingIgnoreCase(title);

        return tasks.stream()
                .map(TaskMapper::toResponseDto)
                .toList();
    }


    public TaskResponseDTO updateById(Long id, TaskRequestDTO updatedTask){

        logger.info("updating the task with the id:{}",id);

        Task existingTask=taskRepository.findById(id)
                .orElseThrow(()->new TaskNotFoundException("Task not found  with this id:"+ id));
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());

        logger.info("updated the task with the id:{}",existingTask.getId());

        return TaskMapper.toResponseDto(taskRepository.save(existingTask));
    }

    public ResponseEntity<String> deleteTaskById(Long id){

        logger.info("Deleting the task with the id:{}",id);

        if(!taskRepository.existsById(id)) ResponseEntity.status(404).body("Task not found wih this id:"+id);

        taskRepository.deleteById(id);

        logger.info("Deleted the task");

        return ResponseEntity.ok("Task Deleted Successfully");

    }



}
