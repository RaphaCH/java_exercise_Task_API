package com.task.task.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.task.model.Department;
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
        var response = departmentServices.retrieveAllDepartments();
        return response;
    }

    @PostMapping()
    public ResponseEntity<?> createDepartment(@RequestBody @Valid Department department) {
        ResponseEntity<?> response = departmentServices.createNewDeparment(department);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneDepartment(@PathVariable("id") long id) {
        ResponseEntity<?> response = departmentServices.deleteOneDepartment(id);
        return response;
    }
}
