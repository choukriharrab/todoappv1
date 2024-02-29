package com.example.TasksMS.Repository;

import com.example.TasksMS.Entity.Task;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    boolean existsById(@NonNull Long id); // Method to check if a task ID exists

    public List<Task> findByUserId(Long userId);


}
