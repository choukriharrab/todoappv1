package com.example.demo.Repository;

import com.example.demo.Entity.Task;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    boolean existsById(@NonNull Long id); // Method to check if a task ID exists

}
