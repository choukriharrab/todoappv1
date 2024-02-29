package com.example.UsersMS.Repository;

import com.example.UsersMS.Entity.User;
import com.example.UsersMS.MyExceptions.NotFoundException;
import com.example.UsersMS.Utils.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public void deleteUserById(Long id) throws NotFoundException;

}
