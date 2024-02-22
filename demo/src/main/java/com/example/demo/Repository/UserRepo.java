package com.example.demo.Repository;

import com.example.demo.Entity.User;
import com.example.demo.MyExceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public void deleteUserById(Long id) throws NotFoundException;
}
