package com.task.task.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.task.task.model.Employee;
import com.task.task.restTemplate.RestTemplate;

@Service
public class EmployeeServices {

  @Autowired
  private RestTemplate restTemplate;

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(EmployeeServices.class);

  public ResponseEntity<?> getAllEmployees() {
    ResponseEntity<?> response = restTemplate.getAll("http://localhost:8080/employees", Employee.class).block();
    return response;
  }

  public ResponseEntity<?> getOneEmployee(long id) {
    ResponseEntity<?> responseEntity = restTemplate.getOne("http://localhost:8080/employees/" + id, Object.class)
        .block();
    log.info("Contents of responseEntity are {}", responseEntity);
    return responseEntity;
  }

  public ResponseEntity<?> createNewEmployee(Employee employeeDto) {
    HttpEntity<Employee> request = new HttpEntity<>(employeeDto);
    ResponseEntity<?> response = restTemplate.post("http://localhost:8080/employees", request, Employee.class).block();
    return response;
  }

  public ResponseEntity<?> deleteOneEmployee(long id) {
    ResponseEntity<?> response = restTemplate.delete("http://localhost:8080/employees/" + id, String.class).block();
    return response;
  }

  public ResponseEntity<?> updateOneEmployee(Long id, Long dptId, Employee employeeDto) {
      HttpEntity<Employee> request = new HttpEntity<>(employeeDto);
      ResponseEntity<?> response = restTemplate
          .put("http://localhost:8080/employees/" + id + "/" + dptId, request, Employee.class)
          .block();
      return response;
  }
  
}
