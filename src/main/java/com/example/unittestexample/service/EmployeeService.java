package com.example.unittestexample.service;

import com.example.unittestexample.dto.EmployeeDTO;
import com.example.unittestexample.entity.Employee;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


public interface EmployeeService {

    Employee find(@NotNull Long id);

    List<Employee> findAll();

    Long save(@NotNull @Valid EmployeeDTO employee);

    void delete(@NotNull Long id);
}
