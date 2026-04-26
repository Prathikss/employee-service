package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@DisplayName("EmployeeController Unit Tests")
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee emp1;
    private Employee emp2;

    @BeforeEach
    void setUp() {
        emp1 = new Employee();
        emp1.setId(1L);
        emp1.setName("Raj Kumar");
        emp1.setRole("Engineer");

        emp2 = new Employee();
        emp2.setId(2L);
        emp2.setName("Priya Singh");
        emp2.setRole("Manager");
    }

    // ── GET /employees ───────────────────────────────────────────

    @Test
    @DisplayName("GET /employees should return 200 OK")
    void getAll_shouldReturn200() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(emp1, emp2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /employees should return list of employees")
    void getAll_shouldReturnEmployeeList() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(emp1, emp2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("GET /employees should return correct names")
    void getAll_shouldReturnCorrectNames() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(emp1, emp2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Raj Kumar")))
                .andExpect(jsonPath("$[1].name", is("Priya Singh")));
    }

    @Test
    @DisplayName("GET /employees should return correct roles")
    void getAll_shouldReturnCorrectRoles() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(emp1, emp2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].role", is("Engineer")))
                .andExpect(jsonPath("$[1].role", is("Manager")));
    }

    @Test
    @DisplayName("GET /employees should return empty array when no employees")
    void getAll_shouldReturnEmptyArray() throws Exception {
        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /employees should return JSON content type")
    void getAll_shouldReturnJsonContentType() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(emp1));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /employees should return correct IDs")
    void getAll_shouldReturnCorrectIds() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(emp1, emp2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    // ── POST /employees ──────────────────────────────────────────

    @Test
    @DisplayName("POST /employees should return 200 OK")
    void save_shouldReturn200() throws Exception {
        when(service.save(any(Employee.class))).thenReturn(emp1);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp1)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /employees should return saved employee")
    void save_shouldReturnSavedEmployee() throws Exception {
        when(service.save(any(Employee.class))).thenReturn(emp1);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Raj Kumar")))
                .andExpect(jsonPath("$.role", is("Engineer")));
    }

    @Test
    @DisplayName("POST /employees should return employee with ID")
    void save_shouldReturnEmployeeWithId() throws Exception {
        when(service.save(any(Employee.class))).thenReturn(emp1);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @DisplayName("POST /employees should call service.save() once")
    void save_shouldCallServiceOnce() throws Exception {
        when(service.save(any(Employee.class))).thenReturn(emp1);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp1)))
                .andExpect(status().isOk());

        verify(service, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("POST /employees should return JSON content type")
    void save_shouldReturnJsonContentType() throws Exception {
        when(service.save(any(Employee.class))).thenReturn(emp1);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}