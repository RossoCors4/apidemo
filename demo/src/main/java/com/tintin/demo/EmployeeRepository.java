package com.tintin.demo;

import org.springframework.data.repository.CrudRepository;

import com.tintin.demo.entity.Employee;
import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    boolean existsByEmailAdress(String emailAdress);

    Optional<Employee> findByEmailAdress(String emailAdress);
    
    List<Employee> findAllByLastName(String lastName);

    void removeByEmailAdress(String emailAdress);
}
