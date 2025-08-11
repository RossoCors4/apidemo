package com.tintin.demo;

import org.springframework.data.repository.CrudRepository;

import com.tintin.demo.entity.Employee;
import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends CrudRepository<Employee, String> {
    Employee findByEmailAdress(String id);
    
    List<Employee> findAllByLastName(String lastName);

    void removeByEmailAdress(String emailAdress);
}
