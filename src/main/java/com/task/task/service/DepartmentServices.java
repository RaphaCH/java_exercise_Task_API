package com.task.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.task.task.model.Department;
import com.task.task.webClient.TaskWebClient;

@Service
public class DepartmentServices {

    @Autowired
    private TaskWebClient webClient;

    public ResponseEntity<?> retrieveAllDepartments() {
        ResponseEntity<?> departments = webClient.getAll("http://localhost:8080/departments", Department.class)
                .block();
        return departments;
    }

    public ResponseEntity<?> createNewDeparment(Department departmentDto) {
            HttpEntity<Department> request = new HttpEntity<>(departmentDto);
            ResponseEntity<?> response = webClient.post("http://localhost:8080/departments", request, Department.class)
                    .block();
            return response;
    }

    public ResponseEntity<?> deleteOneDepartment(long id) {
        ResponseEntity<?> response = webClient.delete("http://localhost:8080/departments/" + id, Object.class).block();
        return response;
    }

}
