package com.example.demo.Service;

import com.example.demo.Entity.DTO.UserRequestDto;
import com.example.demo.Entity.DTO.UserResponseDto;
import com.example.demo.MyExceptions.NoUserFound;
import com.example.demo.MyExceptions.UserAlreadyExists;
import com.example.demo.MyExceptions.UserNotFound;
import com.example.demo.Repository.UserRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers() throws NoUserFound;
    UserResponseDto createUser(UserRequestDto userDto) throws UserAlreadyExists;
    void deleteUser(Long id) throws UserNotFound;
    Object getUserById(Long id) throws UserNotFound;
    UserResponseDto addUser(UserRequestDto userDto);
    UserResponseDto updateUser(Long id, UserRequestDto userDto) throws UserNotFound;
    boolean isValidEmail();

}
