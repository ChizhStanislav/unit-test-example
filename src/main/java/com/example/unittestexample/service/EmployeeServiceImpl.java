package com.example.unittestexample.service;

import com.example.unittestexample.dto.EmployeeDTO;
import com.example.unittestexample.entity.Employee;
import com.example.unittestexample.exception.ExampleException;
import com.example.unittestexample.repository.EmployeeRepository;
import com.example.unittestexample.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee find(@NotNull Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ExampleException(format("Employee with id %s wasn't found", id)));
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public Long save(@NotNull @Valid EmployeeDTO employeeDTO) {
        var hired = DateUtil.parse(employeeDTO.getHired());
        var employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setHired(hired);
        return employeeRepository.save(employee).getId();
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        employeeRepository.deleteById(id);
    }
}
