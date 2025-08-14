package com.tintin.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tintin.demo.controller.EmployeeController;
import com.tintin.demo.entity.Employee;
import com.tintin.demo.InvalidRequestException;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeRepository employeeRepository;

    @Autowired
    ObjectMapper objectMapper;

    Employee EMPLOYEE_1 = new Employee(1l,"tom@test.com","testsson","tom");
    Employee EMPLOYEE_2 = new Employee(2l,"frank@test.com","Kawasaki","Frank");
    Employee EMPLOYEE_3 = new Employee(3l,"cowboy@test.com","Cowboy","Mister");

    @Test
    void getAllEmployees_success() throws Exception {
        //Setup
        List<Employee> employees = new ArrayList<>(Arrays.asList(EMPLOYEE_1, EMPLOYEE_2, EMPLOYEE_3));

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].emailAdress", is("tom@test.com")));
    }

    @Test
    void getEmployeeById_success()throws Exception {
        Mockito.when(employeeRepository.findById(EMPLOYEE_3.getId())).thenReturn(java.util.Optional.of(EMPLOYEE_3));

        mockMvc.perform(MockMvcRequestBuilders
        .get("/api/3")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.emailAdress", is("cowboy@test.com")));
    }

    @Test
    void createEmployee_success() throws Exception {
        Employee employee = Employee.builder()
        .firstName("Gerald")
        .lastName("Ford")
        .emailAdress("cars@fordmoco.com")
        .build();

        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/add")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(employee));

        mockMvc.perform(mockRequest)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.firstName", is("Gerald")));
    }

    @Test
    void createEmployee_failure()throws Exception{
        Mockito.when(employeeRepository.existsByEmailAdress(EMPLOYEE_1.getEmailAdress())).thenReturn(true);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/add")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(EMPLOYEE_1));

        mockMvc.perform(mockRequest)
        .andExpect(status().isConflict())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void deleteEmployeeById_success()throws Exception{
        Mockito.when(employeeRepository.findById(EMPLOYEE_1.getId())).thenReturn(Optional.of(EMPLOYEE_1));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());   
    }
    @Test
    void deleteEmployeeById_notFound()throws Exception{
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/1"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
                assertTrue(result.getResolvedException() instanceof InvalidRequestException);
                assertEquals("Employee does not exist.", result.getResolvedException().getMessage());
            });
    }

}
