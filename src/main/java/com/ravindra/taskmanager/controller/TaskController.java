package com.ravindra.taskmanager.controller;

import com.ravindra.taskmanager.dto.PagedResponse;
import com.ravindra.taskmanager.dto.TaskResponseDTO;
import com.ravindra.taskmanager.dto.TaskRequestDTO;
import com.ravindra.taskmanager.entity.Task;
import com.ravindra.taskmanager.enums.TaskSortField;
import com.ravindra.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


    @Operation(
            summary="creates a new task",
            description="Creates a new task and store it in PostgreSQL DataBase"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Creates a new task and stores it in DB",
            content = @Content(
                    schema = @Schema(
                            implementation = TaskResponseDTO.class
                    )
            )
    )
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

    @Operation(
            summary="Retrieves the Tasks",
            description = "Retrieves the Tasks based on the ID from the PostgreSQL DB"
    )
    @ApiResponse(
            responseCode="200",
            description="Task retrieved successfully",
            content=@Content(
                    schema=@Schema(
                            implementation=TaskResponseDTO.class
                    )
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getById(@PathVariable  Long id){

        TaskResponseDTO responseDTO=taskService.getById(id);
        //return ResponseEntity.ok(task);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    @Operation(
            summary="Fetches the tasks based on the status"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Fetches the tasks based on the status from the PostgreSQL DB",
            content=@Content(
                    schema=@Schema(
                            implementation = TaskResponseDTO.class
                    )
            )
    )
    @GetMapping("/status/{status}")
    public  ResponseEntity<List<TaskResponseDTO>> getTasksByStatus(
            @Parameter(
                    description="Fetching the tasks from the DB based on the status",
                    example = "Pending or Completed or InProgress"
            )
            @PathVariable String status){
        return ResponseEntity.ok(
                taskService.getTaskByStatus(status)
        );
    }

    @Operation(
            summary="Retrieves the tasks based on the title"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Searches the task based on the title and fetches that matched title",
            content = @Content(
                    schema=@Schema(
                            implementation = TaskResponseDTO.class
                    )
            )
    )
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> searchTasksByTitle(
            @Parameter(
                    description = "Retrieves the tasks based on the title",
                    example="%Spring%, like in sql LIKE operation"
            )
            @RequestParam
            String title){

        return ResponseEntity.ok(
                taskService.searchTasksByTitle(title)
        );
    }

    @Operation(
            summary="Retrieves all the Tasks ",
            description = "Retrieves all the tasks from the PostgreSQL DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieves all the tasks from the DB",
            content=@Content(
                    schema = @Schema(
                            implementation = TaskResponseDTO.class
                    )
            )
    )
    @GetMapping
    public ResponseEntity<PagedResponse<TaskResponseDTO>> getTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,

            @Parameter(
                    description="Zero based page",
                    example="0"
            )
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

    @Operation(
            summary="Updates the task with ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Updates the Task with the ID and returns the updated Task",
            content=@Content(
                    schema=@Schema(
                            implementation = TaskResponseDTO.class
                    )
            )

    )
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO updatedTask) {
        TaskResponseDTO responseDTO = taskService.updateById(id, updatedTask);
        return ResponseEntity.ok(responseDTO);

    }

    @Operation(
            summary = "Delete the task with the ID",
            description = "Deletes the task with the ID from the PostgreSQL DB"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Deletes the task based on the id",
            content=@Content(
                    schema=@Schema(
                            implementation = TaskResponseDTO.class
                    )
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long id) {
        return taskService.deleteTaskById(id);
    }
}
