package com.task.task.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    
    private Long id;

    @NotBlank(message = "Providing a first name is mandatory")
    private String firstName;

    @NotBlank(message = "Providing a last name is mandatory")
    private String lastName;

    @NotNull(message = "Providing a salary value is mandatory. It can be a number with decimals.")
    private Double salary;

    @NotNull(message = "Providing an age value is mandatory. It must be a number")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    private String jobTitle;

    private Department department;

}
