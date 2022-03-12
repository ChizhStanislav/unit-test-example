package com.example.unittestexample.service;

import com.example.unittestexample.dto.EmployeeDTO;
import com.example.unittestexample.entity.Employee;
import com.example.unittestexample.exception.ExampleException;
import com.example.unittestexample.repository.EmployeeRepository;
import com.example.unittestexample.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.ConstraintViolationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmployeeServiceImpl.class, MethodValidationPostProcessor.class})
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void shouldReturnEmployee_whenFind_givenId() {
        // arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee()));

        // act
        var actual = employeeService.find(1L);

        // assert
        assertNotNull(actual);
    }

    @Test
    void shouldThrowException_whenFind_givenIdNull() {
        // arrange

        // act

        // assert
        var actual = assertThrows(ConstraintViolationException.class, () -> employeeService.find(null));
        assertEquals("find.id: must not be null", actual.getMessage());
    }

    @Test
    void shouldThrowException_whenFind_givenNotExistId() {
        // arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // act

        // assert
        var actual = assertThrows(ExampleException.class, () -> employeeService.find(1L));
        assertEquals("Employee with id 1 wasn't found", actual.getMessage());
    }

    @Test
    void shouldReturnListOfEmployee_whenFindAll_givenNothing() {
        // arrange
        when(employeeRepository.findAll()).thenReturn(List.of(new Employee(), new Employee()));

        // act
        var actual = employeeService.findAll();

        // assert
        assertEquals(2, actual.size());
    }

    @Test
    void shouldReturnId_whenSave_givenEmployeeDTO() {
        try (var dateUtil = mockStatic(DateUtil.class)) {
            // arrange
            var employeeDTO = new EmployeeDTO();
            employeeDTO.setName("name");
            employeeDTO.setHired("date");

            var expected = new Employee();
            expected.setId(1L);

            when(employeeRepository.save(any(Employee.class))).thenReturn(expected);
            dateUtil.when(() -> DateUtil.parse("date")).thenReturn(LocalDate.now());

            // act
            var actual = employeeService.save(employeeDTO);

            // assert
            assertEquals(1L, actual);
        }
    }

    @Test
    void shouldThrowException_whenSave_givenEmployeeNameBlank() {
        // arrange
        var employeeDTO = new EmployeeDTO();
        employeeDTO.setName("");
        employeeDTO.setHired("date");

        // act

        // assert
        var actual = assertThrows(ConstraintViolationException.class, () -> employeeService.save(employeeDTO));
        assertEquals("save.employee.name: must not be blank", actual.getMessage());
    }

    @Test
    void shouldThrowException_whenDelete_givenIdNull() {
        // arrange

        // act

        // assert
        var actual = assertThrows(ConstraintViolationException.class, () -> employeeService.delete(null));
        assertEquals("delete.id: must not be null", actual.getMessage());
    }

    @Test
    void shouldReturnNothing_whenDelete_givenId() {
        // arrange
        doNothing().when(employeeRepository).deleteById(1L);

        // act
        employeeService.delete(1L);

        // assert
        verify(employeeRepository).deleteById(1L);
    }
}