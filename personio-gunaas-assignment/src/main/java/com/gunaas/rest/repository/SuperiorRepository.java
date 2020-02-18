package com.gunaas.rest.repository;

import com.gunaas.rest.entity.EmployeeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperiorRepository {
    public List<EmployeeEntity> getSuperiors(String employeeName);
}