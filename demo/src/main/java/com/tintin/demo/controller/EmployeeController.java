package com.tintin.demo.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Employee> getById(@PathVariable Long id){
        return employeeRepository.findById(id)
                                    .map(ResponseEntity::ok)
                                    .orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<?> postMethodName(@RequestBody Employee employee) {
        if(employeeRepository.existsByEmailAdress(employee.getEmailAdress())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use!");
        }
        Employee saved = employeeRepository.save(employee);
        return ResponseEntity.created(URI.create("/api/"+saved.getId())).body(saved);
    }
    @DeleteMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id")long id, @RequestBody Employee employee){
        employeeRepository.deleteById(id);
    }
    
}
