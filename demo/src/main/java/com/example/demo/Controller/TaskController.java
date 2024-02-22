package com.example.demo.Controller;

import com.example.demo.Entity.DTO.TaskRequestDto;
import com.example.demo.MyExceptions.*;
import com.example.demo.Service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    // add CRUD methods
    // add ExceptionHandling
    // create custom exceptions NotFoundException, DataNotValidException, etc.
    // BONUS : Add Swagger documentation (OpenAPI)!
    //Read
    @GetMapping("/")
    public ResponseEntity<?> getAllTasks() throws NotFoundException {
        if (taskService.getAllTasks().isEmpty()) {
            throw new NotFoundException("No Task Found!");
        } else {
            return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
        }
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) throws NotFoundException {
        if(taskService.isTaskIdExists(id)){
            return new ResponseEntity<>(taskService.getTaskById(id),HttpStatus.OK);
        }
        throw new NotFoundException("Task with id: "+id +" doesn't Exist!");
    }
    //Create
    @PostMapping("/create")
    @Validated
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequestDto taskDto, BindingResult bindingResult)  {

                return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.CREATED);
    }
    //Update
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskRequestDto userDto) {
        try {
            return new ResponseEntity<>(taskService.updateTask(id,userDto), HttpStatus.ACCEPTED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
    //Delete
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) throws NotFoundException {
        try {
            taskService.deleteTask(id);
            return "Task id: "+id+" deleted!";
        } catch (NotFoundException e) {
            throw new NotFoundException("Task Not Found!");
        }
    }

}

