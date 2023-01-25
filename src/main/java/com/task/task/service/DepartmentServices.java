package com.task.task.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.task.task.model.Department;
import com.task.task.restTemplate.RestTemplate;

@Service
public class DepartmentServices {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(DepartmentServices.class);

    public ResponseEntity<?> retrieveAllDepartments() {
        ResponseEntity<?> departments = restTemplate.getAll("http://localhost:8080/departments", Department.class)
                .block();
        return departments;
    }

    public ResponseEntity<?> createNewDeparment(Department departmentDto) {
            HttpEntity<Department> request = new HttpEntity<>(departmentDto);
            ResponseEntity<?> response = restTemplate.post("http://localhost:8080/departments", request, Department.class)
                    .block();
            return response;
    }

}
