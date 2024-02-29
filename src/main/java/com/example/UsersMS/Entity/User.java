package com.example.UsersMS.Entity;

import com.example.UsersMS.Utils.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    @NotNull(message = "First name cannot be null!")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last name cannot be null!")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Transient
    private List<Task> tasks;
}
