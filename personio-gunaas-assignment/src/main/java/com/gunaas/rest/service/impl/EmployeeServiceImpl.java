package com.gunaas.rest.service.impl;

import com.gunaas.rest.entity.EmployeeEntity;
import com.gunaas.rest.repository.SuperiorRepository;
import com.gunaas.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Scope("prototype")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private SuperiorRepository superiorRepository;

    @NotNull
    public List<EmployeeEntity> getSuperiors(@NotNull String employeeName) {
        return superiorRepository.getSuperiors(employeeName);
    }
}