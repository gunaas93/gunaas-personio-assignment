package com.gunaas.rest.repository;

import com.gunaas.rest.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    public EmployeeEntity findEmployeeEntityByEmployeeName(String employeeName);

    public int deleteEmployeeEntityByEmployeeName(String employeeName);
}
