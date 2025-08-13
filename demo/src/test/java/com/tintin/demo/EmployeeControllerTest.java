package com.tintin.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tintin.demo.controller.EmployeeController;
import com.tintin.demo.entity.Employee;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeRepository employeeRepository;

    Employee EMPLOYEE_1 = new Employee(1l,"tom@test.com","testsson","tom");
    Employee EMPLOYEE_2 = new Employee(2l,"frank@test.com","Kawasaki","Frank");
    Employee EMPLOYEE_3 = new Employee(3l,"cowboy@test.com","Cowboy","Mister");

    @Test
    void getAllEmployees_success() throws Exception {
        //Setup
        List<Employee> employees = new ArrayList<>(Arrays.asList(EMPLOYEE_1, EMPLOYEE_2, EMPLOYEE_3));

        //Mockito.when()

        //Then
    }

}
