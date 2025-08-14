package com.tintin.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tintin.demo.EmployeeRepository;
import com.tintin.demo.InvalidRequestException;
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
    public ResponseEntity<?> postEmployee(@RequestBody Employee employee) throws ResponseStatusException {
        if(employeeRepository.existsByEmailAdress(employee.getEmailAdress())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee with that email already exists!");
        }
        Employee saved = employeeRepository.save(employee);
        return ResponseEntity.created(URI.create("/api/"+saved.getId())).body(saved);
    }
    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")long id)throws InvalidRequestException{
        if(employeeRepository.findById(id).isEmpty()){
            throw new InvalidRequestException("Employee does not exist.");
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();//204
    }
    
}
