package com.task.task.service;

import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.task.task.model.CustomException;
import com.task.task.model.Employee;
import com.task.task.restTemplate.RestTemplate;

@Service
public class EmployeeServices {

  @Autowired
  private RestTemplate restTemplate;

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(EmployeeServices.class);

  public List<Employee> getAllEmployees() throws CustomException {
    // RestTemplate restTemplate = new RestTemplate("localhost:8080");
    try {
      // List<Employee> response = restTemplate.get("http://localhost:8080/employees", List.class).block();
      List<Employee> response = restTemplate.getAll("http://localhost:8080/employees", Employee.class).block();
      return response;
    } catch (RestClientResponseException e) {
      CustomException customException = e.getResponseBodyAs(CustomException.class);
      log.error("Error inside catch statment {}", customException);
      if (customException != null) {
        throw customException;
      }
      return null;
    }
  }

  // private Response
  // convertHttpServerExceptionToErrorMessage(WebClientResponseException e) {
  // String responseString = ((HttpServerErrorException )
  // e).getResponseBodyAsString();
  // ObjectMapper mapper = new ObjectMapper();
  // try {
  // return mapper.readValue(responseString, Response.class);
  // } catch (JsonProcessingException processingError) {
  // return new Response(false, "Error while processing the response from the
  // server", HttpStatus.INTERNAL_SERVER_ERROR, null);
  // }
  // }

  // private Response
  // convertHttpClientExceptionToErrorMessage(WebClientResponseException e) {
  // String responseString = ((HttpClientErrorException )
  // e).getResponseBodyAsString();
  // ObjectMapper mapper = new ObjectMapper();
  // try {
  // return mapper.readValue(responseString, Response.class);
  // } catch (JsonProcessingException processingError) {
  // return new Response(false, "Error while processing the response from the
  // client", HttpStatus.INTERNAL_SERVER_ERROR, null);
  // }
  // }

  public Employee getOneEmployee(long id) throws CustomException {
    try {
      Employee response = restTemplate.get("http://localhost:8080/employees/" + id, Employee.class).block();
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

  public Employee createNewEmployee(Employee employeeDto) {
    HttpEntity<Employee> request = new HttpEntity<>(employeeDto);
    Employee response = restTemplate.post("http://localhost:8080/employees", request, Employee.class).block();
    return response;
  }

  public String deleteOneEmployee(long id) {
    String response = restTemplate.delete("http://localhost:8080/employees/" + id, String.class).block();
    return response;
  }

  public Employee updateOneEmployee(Long id, Long dptId, Employee employeeDto) {
    HttpEntity<Employee> request = new HttpEntity<>(employeeDto);
    Employee response = restTemplate.put("http://localhost:8080/employees/" + id + "/" + dptId, request, Employee.class)
        .block();
    return response;
  }

}
