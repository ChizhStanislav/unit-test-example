package com.example.unittestexample.controller;

import com.example.unittestexample.dto.EmployeeDTO;
import com.example.unittestexample.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable("id") @NotNull Long id) {
        var employee = employeeService.find(id);
        var employeeDTO = new EmployeeDTO(employee.getId(), employee.getName(), employee.getHired().toString());
        return ResponseEntity.ok(employeeDTO);
    }
}
