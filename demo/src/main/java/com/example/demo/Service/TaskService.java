package com.example.demo.Service;

import com.example.demo.Entity.DTO.TaskRequestDto;
import com.example.demo.Entity.DTO.TaskResponseDto;
import com.example.demo.MyExceptions.AlreadyExistsException;
import com.example.demo.MyExceptions.NotFoundException;

import java.util.List;

public interface TaskService {
    public boolean isTaskIdExists(Long taskId);
    List<TaskResponseDto> getAllTasks() throws NotFoundException;
    TaskResponseDto createTask(TaskRequestDto taskDto) throws AlreadyExistsException;
    TaskResponseDto getTaskById(Long id) throws NotFoundException;
    TaskResponseDto updateTask(Long id, TaskRequestDto taskDto) throws NotFoundException;
    void deleteTask(Long id) throws NotFoundException;
//    public boolean isValidDueDate(Date dueDate);
}