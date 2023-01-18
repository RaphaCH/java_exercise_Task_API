package com.task.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.task.model.CustomException;
import com.task.task.model.Department;
import com.task.task.model.ErrorMessage;
import com.task.task.service.DepartmentServices;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentServices departmentServices;

    @GetMapping()
    public ResponseEntity<?> getAllDepartments() {
        try {
            List<Department> response = departmentServices.retrieveAllDepartments();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getStatusCode(), e.getErrorMessage(), e.getSubcode(),
                    e.getDetails());
            HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(errorMessage.getStatusCode()));
            return new ResponseEntity<>(errorMessage, httpStatus);
        }
    }

    @PostMapping()
    public ResponseEntity<?> createDepartment(@RequestBody @Valid Department department) {
        try {
            Department response = departmentServices.createNewDeparment(department);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (CustomException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getStatusCode(), e.getErrorMessage(), e.getSubcode(),
                    e.getDetails());
            HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(errorMessage.getStatusCode()));
            return new ResponseEntity<>(errorMessage, httpStatus);
        }

    }
}
