package com.example.demo.Service;

import com.example.demo.Entity.DTO.TaskRequestDto;
import com.example.demo.Entity.DTO.TaskResponseDto;
import com.example.demo.Entity.Task;
import com.example.demo.MyExceptions.AlreadyExistsException;
import com.example.demo.MyExceptions.DataNotValidException;
import com.example.demo.MyExceptions.EmptyEntryException;
import com.example.demo.MyExceptions.NotFoundException;
import com.example.demo.Repository.TaskRepo;
import com.example.demo.Utils.DPCombinator.TaskRegistrationValidator;
import com.example.demo.Utils.MappingProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static com.example.demo.Utils.DPCombinator.TaskRegistrationValidator.*;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepo taskRepo;
    public boolean isTaskIdExists(Long taskId) {
        return taskRepo.existsById(taskId);
    }
    @Override
    public List<TaskResponseDto> getAllTasks() throws NotFoundException {
        if (taskRepo.findAll().isEmpty()) {
            throw new NotFoundException("No Task Found!");
        }else {
            return taskRepo.findAll().stream()
                    .map(MappingProfile::mapToDto)
                    .toList();
        }
    }
    @Override
    public TaskResponseDto createTask(TaskRequestDto taskDto) throws EmptyEntryException, DataNotValidException {
        if (taskDto.getTitle().isEmpty()) throw new EmptyEntryException("Empty title entry!");
        if (taskDto.getStatus().describeConstable().isEmpty()) throw new EmptyEntryException("Empty status entry!");
        if (!isValidDueDate(taskDto.getDueDate().toString()).apply(taskDto)) throw new DataNotValidException("Invalid date format - Use yyyy-MM-dd instead!");
        if (taskDto.getUserId().describeConstable().isEmpty()) throw new EmptyEntryException("Empty User id entry!");
        if (taskRepo.existsById(taskDto.getUserId()))throw new AlreadyExistsException("Nonexistent User id entry! id: " + taskDto.getUserId());
        var task = MappingProfile.mapToEntity(taskDto);
        return MappingProfile.mapToDto(taskRepo.save(task));

    }
    @Override
    public TaskResponseDto getTaskById(Long id) throws NotFoundException {
            Task task = taskRepo.findById(id).orElseThrow(()->new NotFoundException("Task with id: "+id+" does NOT exist!"));
            return MappingProfile.mapToDto(task);
    }
    @Override
    public TaskResponseDto updateTask(Long id, TaskRequestDto taskDto) throws NotFoundException, DataNotValidException {
        var task = taskRepo.findById(id).orElseThrow(()->new NotFoundException("Task with id: "+id+" does NOT exist!"));
        if (taskDto.getTitle()!=null)task.setTitle(taskDto.getTitle());
        if (taskDto.getDescription()!=null)task.setDescription(taskDto.getDescription());
        if (taskDto.getUserId()!=null)taskDto.setUserId(taskDto.getUserId());
        if (taskDto.getStatus()!=null)task.setStatus(taskDto.getStatus());
        if (isValidDueDate(taskDto.getDueDate().toString()).apply(taskDto)) throw new DataNotValidException("Invalid date format - Use yyyy-MM-dd instead!");
        if (task.getDueDate()!=null) task.setDueDate(task.getDueDate());
        return MappingProfile.mapToDto(taskRepo.save(task));
    }
    @Override
    @Transactional
    public void deleteTask(Long id) throws NotFoundException {
        if (taskRepo.existsById(id)) {
            taskRepo.deleteById(id);
        }else {
            throw new NotFoundException("Task Not Found! (Can't delete nonexistent things!!)");
        }
    }
}
