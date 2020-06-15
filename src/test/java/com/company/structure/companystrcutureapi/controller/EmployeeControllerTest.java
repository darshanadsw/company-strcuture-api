package com.company.structure.companystrcutureapi.controller;

import com.company.structure.companystrcutureapi.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveEmployee() throws Exception {

        mockMvc.perform(post("/employee")
                .param("name","First Name")
                .param("department.name","Department Name")
                .param("salary","720000"))
                .andExpect(status().isOk());

    }

    @Test
    void employeePage() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(view().name("employee"))
                .andExpect(status().isOk());
    }
}