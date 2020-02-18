package com.gunaas.rest.service;

import com.gunaas.rest.entity.EmployeeEntity;
import com.gunaas.rest.repository.SuperiorRepository;
import com.gunaas.rest.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.PotentialStubbingProblem;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService = new EmployeeServiceImpl();

    @Mock
    private SuperiorRepository superiorRepository;

    @BeforeEach
    public void setup() {
        EmployeeEntity superior = new EmployeeEntity("Boss");
        Mockito.when(superiorRepository.getSuperiors("Employee")).thenReturn(Arrays.asList(superior));
    }

    @Test
    public void henValidStaffName_thenBossShouldBeFound() {
        List<EmployeeEntity> employeeEntities = employeeService.getSuperiors("Employee");
        Assertions.assertEquals(employeeEntities.get(0).getEmployeeName(), "Boss");
    }

    @Test
    public void whenInvalidStaffName_thenBossShouldNotBeFound() {
        Assertions.assertThrows(PotentialStubbingProblem.class, () -> employeeService.getSuperiors("employee doesn't exist"));
    }
}
