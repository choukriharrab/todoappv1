package com.example.demo.Controller;

import com.example.demo.Entity.DTO.UserRequestDto;
import com.example.demo.MyExceptions.*;
import com.example.demo.Service.UserServiceImp;
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
    public ResponseEntity<?> getAllUsers() throws NoUserFound {
        if (userService.getAllUsers().isEmpty()) {
            throw new NoUserFound();
        } else {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws UserNotFound {
        try {
            return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
        } catch (UserNotFound e) {
            return new ResponseEntity<>("User Not Found!",HttpStatus.NOT_FOUND);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
    //Create
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userDto) throws UserAlreadyExists {

        try {
            return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
        } catch (UserAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    //Update
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userDto) throws UserNotFound {
        try {
            return new ResponseEntity<>(userService.updateUser(id,userDto), HttpStatus.ACCEPTED);
        } catch (UserNotFound e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
    //Delete
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) throws UserNotFound {
        try {
            userService.deleteUser(id);
            return "User id: "+id+" deleted!";
        } catch (UserNotFound e) {
            return new UserNotFound()+"\nUser Not Found!";
        }
    }

}
