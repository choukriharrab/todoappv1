package com.example.demo.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}