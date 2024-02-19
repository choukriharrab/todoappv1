package com.example.demo.Service;

import com.example.demo.Entity.DTO.TaskRequestDto;
import com.example.demo.Entity.DTO.TaskResponseDto;
import com.example.demo.MyExceptions.NoTaskFound;
import com.example.demo.MyExceptions.TaskNotFound;
import com.example.demo.Repository.TaskRepo;
import com.example.demo.Utils.MappingProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepo taskRepo;

    @Override
    public List<TaskResponseDto> getAllTasks() throws NoTaskFound {
        if (taskRepo.findAll().isEmpty()) {
            throw new NoTaskFound();
        }else {
            return taskRepo.findAll().stream()
                    .map(MappingProfile::mapToDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public TaskResponseDto createTask(TaskRequestDto taskDto) {
        var task = MappingProfile.mapToEntity(taskDto);
        return MappingProfile.mapToDto(taskRepo.save(task));
    }

    @Override
    public TaskResponseDto getTaskById(Long id) throws TaskNotFound {
        var task = taskRepo.findById(id).orElseThrow(TaskNotFound::new);
        return MappingProfile.mapToDto(task);
    }

    @Override
    public TaskResponseDto updateTask(Long id, TaskRequestDto taskDto) throws TaskNotFound {
        var task = taskRepo.findById(id).orElseThrow(TaskNotFound::new);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        taskDto.setUserId(taskDto.getUserId());
        task.setStatus(taskDto.getStatus());
        task.setDueDate(task.getDueDate());
        return MappingProfile.mapToDto(taskRepo.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long id) throws TaskNotFound {
        if (taskRepo.existsById(id)) {
            taskRepo.deleteById(id);
        }else {
            System.err.println("Task Not Found!");
            throw new TaskNotFound();

        }
    }


}
