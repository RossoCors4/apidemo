package com.tintin.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tintin.demo.EmployeeRepository;
import com.tintin.demo.entity.Employee;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("api")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    //add
    //remove
    //getall
    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseBody
    public Optional<Employee> getEmployees(@PathVariable String id){
        return employeeRepository.findByEmailAdress(id);
    }
    @GetMapping("/list")
    @ResponseBody
    public List<Employee> getAllEmployees() {
    List<Employee> employees = StreamSupport
        .stream(employeeRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
        return employees;
    }
    
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee postMethodName(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }
    @DeleteMapping(value ="/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("email")String email, @RequestBody Employee employee){
        employeeRepository.deleteById(email);
    }
    
}
