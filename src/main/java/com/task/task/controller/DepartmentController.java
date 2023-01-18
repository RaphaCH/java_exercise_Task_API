package com.task.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.task.model.Response;
import com.task.task.service.DepartmentServices;

import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentServices departmentServices;

    

    // @GetMapping()
    // public ResponseEntity<Response> getAllDepartments() {
    //     // Response response = departmentServices.retrieveAllDepartments();
    //     // return new ResponseEntity<Response>(response, response.getStatus());
    // }

    // @PostMapping()
    // public ResponseEntity<Response> createDepartment(@org.springframework.web.bind.annotation.RequestBody @Valid DepartmentDto department ) {
    //     // Response response = departmentServices.createNewDeparment(department);
    //     // return new ResponseEntity<Response>(response, response.getStatus());
    // }
}
