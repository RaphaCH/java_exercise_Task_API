package com.task.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.task.model.CustomException;
import com.task.task.model.Employee;
import com.task.task.model.ErrorMessage;
import com.task.task.service.EmployeeServices;

//import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServices employeeServices;

    @GetMapping("/alive")
    public String alive() {
        return "I'm alive!";
    }

    @GetMapping()
    // The operation annotation is method specific.
    // The TAG argument could override the default organization of every CRUD
    // operation being held under same controller class
    // and be used to bundle together CRUD operations that have not originally been
    // place in the same controller.
    // Check how our team prefers the organization of the project.
    @Operation(tags = {
            "Employee Api contoller" }, summary = "Get operation to retrieve all stored Employees on the DB.", description = "Simple get method with no extra params required to display all currently stored Employees inside of the Database.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = false, description = "Leave request body empty."), responses = {
                    @ApiResponse(responseCode = "200", description = "Response returned both when there are and there are not Employees saved in the Database.", useReturnTypeSchema = true),
            }
    // externalDocs = @ExternalDocumentation(),
    )
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<Employee> response = employeeServices.getAllEmployees();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getStatusCode(), e.getErrorMessage(), e.getSubCode(),
                    e.getDetails());
            HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(errorMessage.getStatusCode()));
            return new ResponseEntity<>(errorMessage, httpStatus);
        }
    }

    @GetMapping("/{id}")
    @Operation(tags = {
            "Employee Api contoller" }, summary = "Get one Employee from the Database, if it exists.", description = "Get one employee from the database by providing his/her id in as a path parameter. Example: writing 1 on the path will retrieve the Employee with id 1, if he/she exists in the Database.", parameters = {
                    @Parameter(name = "id", example = "1", description = "Provide it as a number on the url path {id} where indicated") }, responses = {
                            @ApiResponse(responseCode = "200", description = "Response 200-ok whenever the provided id returns one found Employee from the Database.", useReturnTypeSchema = true, content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Employee.class))),
                            @ApiResponse(responseCode = "404", description = "Response returned whenever the provided id does not find any Emoployee in the Database.")
                    })
    public ResponseEntity<?> getOneEmployee(@PathVariable("id") long id) {
        try {
            Employee response = employeeServices.getOneEmployee(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getStatusCode(), e.getErrorMessage(), e.getSubCode(),
                    e.getDetails());
            HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(errorMessage.getStatusCode()));
            return new ResponseEntity<>(errorMessage, httpStatus);
        }

    }

    @PostMapping()
    @Operation(tags = { "Employee Api contoller" })
    public ResponseEntity<?> createNewEmployee(@RequestBody @Valid Employee employee) {
        Employee response = employeeServices.createNewEmployee(employee);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(tags = { "Employee Api contoller" })
    public ResponseEntity<?> deleteOneEmployee(@PathVariable("id") long id) {
        String response = employeeServices.deleteOneEmployee(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/{dptId}")
    @Operation(tags = { "Employee Api contoller" })
    public ResponseEntity<?> updateOneEmployee(@PathVariable("id") Long id,
            @PathVariable(required = false, name = "dptId") Long dptId, @RequestBody @Valid Employee employee) {
        Employee response = employeeServices.updateOneEmployee(id, dptId, employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
