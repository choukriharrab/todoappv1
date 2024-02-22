package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
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

    //Check if null
    @Column(name = "first_name")
    @NotNull(message = "First name cannot be null!")
    private String firstName;

    //Check if null
    @Column(name = "last_name")
    @NotNull(message = "Last name cannot be null!")
    private String lastName;

    //Check if null & validate email & unique
    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();

}