package com.example.employee.entity;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Employee Entity Unit Tests")
class EmployeeTest {

    @Test
    @DisplayName("Should set and get name correctly")
    void shouldSetAndGetName() {
        Employee emp = new Employee();
        emp.setName("Raj Kumar");
        assertThat(emp.getName()).isEqualTo("Raj Kumar");
    }

    @Test
    @DisplayName("Should set and get role correctly")
    void shouldSetAndGetRole() {
        Employee emp = new Employee();
        emp.setRole("Engineer");
        assertThat(emp.getRole()).isEqualTo("Engineer");
    }

    @Test
    @DisplayName("Should set and get ID correctly")
    void shouldSetAndGetId() {
        Employee emp = new Employee();
        emp.setId(10L);
        assertThat(emp.getId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("Default employee should have null name")
    void defaultEmployeeShouldHaveNullName() {
        Employee emp = new Employee();
        assertThat(emp.getName()).isNull();
    }

    @Test
    @DisplayName("Default employee should have null role")
    void defaultEmployeeShouldHaveNullRole() {
        Employee emp = new Employee();
        assertThat(emp.getRole()).isNull();
    }
}