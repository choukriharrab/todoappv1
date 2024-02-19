package com.example.demo.Service;

import com.example.demo.Entity.DTO.TaskRequestDto;
import com.example.demo.Entity.DTO.TaskResponseDto;
import com.example.demo.MyExceptions.NoTaskFound;
import com.example.demo.MyExceptions.TaskAlreadyExists;
import com.example.demo.MyExceptions.TaskNotFound;

import java.util.Date;
import java.util.List;

public interface TaskService {
    List<TaskResponseDto> getAllTasks() throws NoTaskFound;
    TaskResponseDto createTask(TaskRequestDto taskDto) throws TaskAlreadyExists;
    TaskResponseDto getTaskById(Long id) throws TaskNotFound;
    TaskResponseDto updateTask(Long id, TaskRequestDto taskDto) throws TaskNotFound;
    void deleteTask(Long id) throws TaskNotFound;
//    public boolean isValidDueDate(Date dueDate);
}