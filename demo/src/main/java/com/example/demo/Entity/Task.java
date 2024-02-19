package com.example.demo.Entity;

import com.example.demo.Utils.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //Check if null
    @Column(name = "title")
    @NotNull(message = "Title cannot be null!")
    private String title;
    @Column(name = "description")
    private String description;
    //Change to enum (Checked)
    //Check if null
    @Column(name = "status")
    @NotNull(message = "Status cannot be null!")
    private Status status;

    //Check if null
    @NotNull(message = "Due date cannot be null!")
    //Check if date is valid (due date in the future)
    @Future(message = "Due date must be in the future!")
    @Column(name = "due_date")
    private Date dueDate;


    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;
}
