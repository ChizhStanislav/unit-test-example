package com.example.unittestexample.controller;

import com.example.unittestexample.dto.EmployeeDTO;
import com.example.unittestexample.entity.Employee;
import com.example.unittestexample.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import javax.annotation.PostConstruct;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EmployeeController.class)
class EmployeeControllerTest {

    private static final String EMPLOYEE_FIND_BY_ID_PATH = "/employee/{id}";

    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    private WebTestClient webTestClient;

    @PostConstruct
    public void setup() {
        this.webTestClient = MockMvcWebTestClient
                .bindToController(employeeController)
                .build();
    }


    @Test
    void shouldReturnExecuteInitialLoad_whenLoad_givenAnyOtherScenario() {
        //arrange
        var date = LocalDate.now();
        var employee = new Employee(1L, "name", date);
        when(employeeService.find(1L)).thenReturn(employee);

        //act
        var response = webTestClient
                .get()
                .uri(EMPLOYEE_FIND_BY_ID_PATH, 1)
                .exchange();

        //assert
        var actual = response
                .expectStatus().isOk()
                .returnResult(EmployeeDTO.class)
                .getResponseBody()
                .blockFirst();
        assertEquals(1L, actual.getId());
        assertEquals("name", actual.getName());
        assertEquals(date.toString(), actual.getHired());
    }
}