package com.ravindra.taskmanager.controller;

import com.ravindra.taskmanager.dto.PagedResponse;
import com.ravindra.taskmanager.dto.TaskResponseDTO;
import com.ravindra.taskmanager.dto.TaskRequestDTO;
import com.ravindra.taskmanager.entity.Task;
import com.ravindra.taskmanager.enums.TaskSortField;
import com.ravindra.taskmanager.service.TaskService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

import java.util.Collections;
import java.util.List;

@Validated
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> CreateTask(@Valid @RequestBody TaskRequestDTO requestDTO){

        TaskResponseDTO response= taskService.saveTask(requestDTO);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

//    @GetMapping
//    public ResponseEntity<PagedResponse<TaskResponseDTO>> getAllTasks(
//
//            @RequestParam(defaultValue="0")
//            @Min(value=0, message="Page cannot be negative")
//            int page,
//            @RequestParam (defaultValue="5")
//            @Min(value=0, message="size should be at least 1")
//            @Max(value=100, message="size should not exceed 100")
//            int size,
//            @RequestParam(defaultValue="id")
//            TaskSortField sortBy,
//            @RequestParam (defaultValue="asc")
//            Sort.Direction direction
//    ){
//
//        return ResponseEntity.ok(taskService.getAllTasks(page,size,sortBy,direction));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getById(@PathVariable  Long id){

        TaskResponseDTO responseDTO=taskService.getById(id);
        //return ResponseEntity.ok(task);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/status/{status}")
    public  ResponseEntity<List<TaskResponseDTO>> getTasksByStatus(@PathVariable String status){
        return ResponseEntity.ok(
                taskService.getTaskByStatus(status)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> searchTasksByTitle(@RequestParam String title){

        return ResponseEntity.ok(
                taskService.searchTasksByTitle(title)
        );
    }

    @GetMapping
    public ResponseEntity<PagedResponse<TaskResponseDTO>> getTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,

            @RequestParam(defaultValue = "0")
            @Min(value=0,message="Page  value should be greater than or equal to zero")
            int page,

            @RequestParam(defaultValue="5")
            @Min(value=1,message ="Size should be at least 1")
            @Max(value=100, message="Size should not  be exceed 100")
            int size,

            @RequestParam(defaultValue = "CREATED_AT")
            TaskSortField sortBy,

            @RequestParam(defaultValue = "DESC")
            Sort.Direction direction
    ) {
        return ResponseEntity.ok(
                taskService.searchTasks(
                        title,
                        status,
                        page,
                        size,
                        sortBy,
                        direction

                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO updatedTask) {
        TaskResponseDTO responseDTO = taskService.updateById(id, updatedTask);
        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long id) {
        return taskService.deleteTaskById(id);
    }
}
