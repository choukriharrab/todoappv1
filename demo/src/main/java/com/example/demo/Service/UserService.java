package com.example.demo.Service;

import com.example.demo.Entity.DTO.UserRequestDto;
import com.example.demo.Entity.DTO.UserResponseDto;
import com.example.demo.MyExceptions.AlreadyExistsException;
import com.example.demo.MyExceptions.EmptyEntryException;
import com.example.demo.MyExceptions.NotFoundException;

import java.util.List;

public interface UserService {
    public boolean isUserIdExists(Long taskId);
    List<UserResponseDto> getAllUsers() throws NotFoundException;
    UserResponseDto createUser(UserRequestDto userDto) throws AlreadyExistsException;
    void deleteUser(Long id) throws  NotFoundException, EmptyEntryException;
    Object getUserById(Long id) throws  NotFoundException, EmptyEntryException ;
    UserResponseDto updateUser(Long id, UserRequestDto userDto) throws NotFoundException;

}
