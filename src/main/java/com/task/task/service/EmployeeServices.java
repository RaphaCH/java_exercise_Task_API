package com.task.task.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.task.task.model.Employee;
import com.task.task.restTemplate.RestTemplate;

@Service
public class EmployeeServices {

  @Autowired
  private RestTemplate restTemplate;

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(EmployeeServices.class);

  public ResponseEntity<?> getAllEmployees() {
    ResponseEntity<?> responseEntity = restTemplate.getAll("http://localhost:8080/employees", Employee.class).block();
    return responseEntity;
  }

  public ResponseEntity<?> getOneEmployee(long id) {
    ResponseEntity<?> responseEntity = restTemplate.getOne("http://localhost:8080/employees/" + id, Object.class)
        .block();
    return responseEntity;
  }

  public ResponseEntity<?> createNewEmployee(Employee employeeDto, long dptId) {
    HttpEntity<Employee> request = new HttpEntity<>(employeeDto);
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/employees")
        .queryParam("deptId", dptId);
    String builderToString = uriBuilder.toUriString();
    ResponseEntity<?> responseEntity = restTemplate.post(builderToString, request, Object.class).block();
    return responseEntity;
  }

  public ResponseEntity<?> deleteOneEmployee(long id) {
    ResponseEntity<?> responseEntity = restTemplate.delete("http://localhost:8080/employees/" + id, Object.class)
        .block();
    return responseEntity;
  }

  public ResponseEntity<?> updateOneEmployee(Long id, Long dptId, Employee employeeDto) {
    HttpEntity<Employee> request = new HttpEntity<>(employeeDto);
    ResponseEntity<?> responseEntity = restTemplate
        .put("http://localhost:8080/employees/" + id + "/" + dptId, request, Employee.class)
        .block();
    return responseEntity;
  }

}
