package com.task.task.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.task.task.model.CustomException;
import com.task.task.model.Department;
import com.task.task.restTemplate.RestTemplate;

@Service
public class DepartmentServices {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(DepartmentServices.class);

    public List<Department> retrieveAllDepartments() throws CustomException {
        try {
            List<Department> departments = restTemplate.getAll("http://localhost:8080/departments", Department.class)
                    .block();
            return departments;
        } catch (WebClientResponseException e) {
            CustomException customException = e.getResponseBodyAs(CustomException.class);
            log.error("Error inside catch statment {}", customException);
            if (customException != null) {
                throw customException;
            }
            return null;
        }
    }

    public Department createNewDeparment(Department departmentDto) throws CustomException {
        try {
            HttpEntity<Department> request = new HttpEntity<>(departmentDto);
            Department response = restTemplate.post("http://localhost:8080/departments", request, Department.class).block();
            return response;
        } catch (WebClientResponseException e) {
            CustomException customException = e.getResponseBodyAs(CustomException.class);
            log.error("Error inside catch statment {}", customException);
            if (customException != null) {
                throw customException;
            }
            return null;
        }
    }

}
