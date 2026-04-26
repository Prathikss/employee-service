package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeService Unit Tests")
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

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

    // ── SAVE TESTS ───────────────────────────────────────────────

    @Test
    @DisplayName("save() should return saved employee")
    void save_shouldReturnSavedEmployee() {
        when(repository.save(any(Employee.class))).thenReturn(emp1);

        Employee result = service.save(emp1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Raj Kumar");
        assertThat(result.getRole()).isEqualTo("Engineer");
    }

    @Test
    @DisplayName("save() should call repository.save() exactly once")
    void save_shouldCallRepositoryOnce() {
        when(repository.save(any(Employee.class))).thenReturn(emp1);

        service.save(emp1);

        verify(repository, times(1)).save(emp1);
    }

    @Test
    @DisplayName("save() should persist employee name correctly")
    void save_shouldPersistNameCorrectly() {
        Employee newEmp = new Employee();
        newEmp.setName("Anil Sharma");
        newEmp.setRole("DevOps");

        Employee saved = new Employee();
        saved.setId(3L);
        saved.setName("Anil Sharma");
        saved.setRole("DevOps");

        when(repository.save(any(Employee.class))).thenReturn(saved);

        Employee result = service.save(newEmp);

        assertThat(result.getName()).isEqualTo("Anil Sharma");
    }

    @Test
    @DisplayName("save() should persist employee role correctly")
    void save_shouldPersistRoleCorrectly() {
        when(repository.save(any(Employee.class))).thenReturn(emp2);

        Employee result = service.save(emp2);

        assertThat(result.getRole()).isEqualTo("Manager");
    }

    @Test
    @DisplayName("save() should return employee with generated ID")
    void save_shouldReturnEmployeeWithId() {
        when(repository.save(any(Employee.class))).thenReturn(emp1);

        Employee result = service.save(emp1);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getId()).isGreaterThan(0L);
    }

    // ── GETALL TESTS ─────────────────────────────────────────────

    @Test
    @DisplayName("getAll() should return all employees")
    void getAll_shouldReturnAllEmployees() {
        when(repository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        List<Employee> result = service.getAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("getAll() should return empty list when no employees")
    void getAll_shouldReturnEmptyListWhenNoEmployees() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = service.getAll();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("getAll() should call repository.findAll() exactly once")
    void getAll_shouldCallRepositoryOnce() {
        when(repository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        service.getAll();

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAll() should return correct employee names")
    void getAll_shouldReturnCorrectNames() {
        when(repository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        List<Employee> result = service.getAll();

        assertThat(result).extracting(Employee::getName)
                .containsExactly("Raj Kumar", "Priya Singh");
    }

    @Test
    @DisplayName("getAll() should return correct employee roles")
    void getAll_shouldReturnCorrectRoles() {
        when(repository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        List<Employee> result = service.getAll();

        assertThat(result).extracting(Employee::getRole)
                .containsExactly("Engineer", "Manager");
    }

    @Test
    @DisplayName("getAll() should return single employee when only one exists")
    void getAll_shouldReturnSingleEmployee() {
        when(repository.findAll()).thenReturn(Collections.singletonList(emp1));

        List<Employee> result = service.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Raj Kumar");
    }
}