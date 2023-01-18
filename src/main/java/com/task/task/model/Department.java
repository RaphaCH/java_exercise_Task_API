package com.task.task.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
   
    private Long id;
    @NotBlank(message = "Department name is mandatory")
    private String name;
    private String description;
    private String location;

}
