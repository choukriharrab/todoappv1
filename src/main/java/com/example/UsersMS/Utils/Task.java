package com.example.UsersMS.Utils;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
    public class Task {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private String status;
    private Date dueDate;
}