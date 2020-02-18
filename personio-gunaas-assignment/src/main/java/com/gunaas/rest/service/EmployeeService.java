package com.gunaas.rest.service;

import com.gunaas.rest.entity.EmployeeEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface EmployeeService {
    @NotNull
    List<EmployeeEntity> getSuperiors(@NotNull String employeeId);
}
