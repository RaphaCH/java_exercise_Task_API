package com.task.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.task.task.model.Employee;
import com.task.task.webClient.TaskWebClient;

@Service
public class EmployeeServices {

  @Autowired
  private TaskWebClient webClient;

  public ResponseEntity<?> getAllEmployees() {
    ResponseEntity<?> responseEntity = webClient.getAll("http://localhost:8080/employees", Employee.class).block();
    return responseEntity;
  }

  public ResponseEntity<?> getOneEmployee(long id) {
    ResponseEntity<?> responseEntity = webClient.getOne("http://localhost:8080/employees/" + id, Object.class)
        .block();
    return responseEntity;
  }

  public ResponseEntity<?> createNewEmployee(Employee employeeDto, long dptId) {
    HttpEntity<Employee> request = new HttpEntity<>(employeeDto);
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/employees")
        .queryParam("deptId", dptId);
    String builderToString = uriBuilder.toUriString();
    ResponseEntity<?> responseEntity = webClient.post(builderToString, request, Object.class).block();
    return responseEntity;
  }

  public ResponseEntity<?> deleteOneEmployee(long id) {
    ResponseEntity<?> responseEntity = webClient.delete("http://localhost:8080/employees/" + id, Object.class)
        .block();
    return responseEntity;
  }

  public ResponseEntity<?> updateOneEmployee(Long id, Long dptId, Employee employeeDto) {
    HttpEntity<Employee> request = new HttpEntity<>(employeeDto);
    ResponseEntity<?> responseEntity = webClient
        .put("http://localhost:8080/employees/" + id + "/" + dptId, request, Object.class)
        .block();
    return responseEntity;
  }

}
