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
    public ResponseEntity<?> getAllTasks() throws NoTaskFound {
        if (taskService.getAllTasks().isEmpty()) {
            throw new NoTaskFound();
        } else {
            return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
        }
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) throws TaskNotFound {
        try {
            return new ResponseEntity<>(taskService.getTaskById(id),HttpStatus.OK);
        } catch (TaskNotFound e) {
            throw new TaskNotFound();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Create
    @PostMapping("/create")
    @Validated

    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequestDto taskDto, BindingResult bindingResult) throws TaskAlreadyExists, DataNotValidException {
        try {
            if (bindingResult.hasErrors()) {

                return new ResponseEntity<>(new DataNotValidException()+"Data Not Valid Exception!",HttpStatus.NOT_ACCEPTABLE);
            } else {
                return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.CREATED);
            }
        } catch (TaskAlreadyExists e) {
            throw new DataNotValidException();
        }
    }
    //Update
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskRequestDto userDto) {
        try {
            return new ResponseEntity<>(taskService.updateTask(id,userDto), HttpStatus.ACCEPTED);
        } catch (TaskNotFound e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
    //Delete
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) throws TaskNotFound {
        try {
            taskService.deleteTask(id);
            return "Task id: "+id+" deleted!";
        } catch (TaskNotFound e) {
            return new TaskNotFound()+"\nTask Not Found!";
        }
    }

}

