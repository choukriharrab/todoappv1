package com.example.demo.Controller;

import com.example.demo.Entity.DTO.UserRequestDto;
import com.example.demo.MyExceptions.*;
import com.example.demo.Service.UserServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserController {
    private final UserServiceImp userService;
    //Read
    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() throws NotFoundException {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);

    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws NotFoundException {
            return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);

    }
    //Create
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDto userDto) throws AlreadyExistsException, DataNotValidException {
            return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);

    }

    //Update
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userDto) throws NotFoundException, DataNotValidException {
            return new ResponseEntity<>(userService.updateUser(id,userDto), HttpStatus.ACCEPTED);

    }
    //Delete
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) throws NotFoundException {
            userService.deleteUser(id);
            return "User id: "+id+" deleted!";

    }

}
