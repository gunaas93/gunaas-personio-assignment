package com.gunaas.rest.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employee", indexes = {@Index(columnList = "emp_name", unique = true, name = "emp_name")})
public class EmployeeEntity extends AbstractEntity<Long> {

    @Size(max = 255, min = 1, message = "employee name must be less than or equal to '{max}'")
    @Column(name = "emp_name" , unique = true)
    private String employeeName;

    @Size(max = 15, min = 1, message = "employee gender must be less than or equal to '{max}'")
    @Column(name = "emp_gender")
    private String employeeGender;

    @Size(max = 15, min = 1, message = "employee phone must be less than or equal to '{max}'")
    @Column(name = "emp_phone")
    private String employeePhone;

    public EmployeeEntity(String employeeName) {
        this.employeeName = employeeName;
        this.employeeGender = "Male";
        this.employeePhone = "948700480";
    }

    public EmployeeEntity() {
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(@NotNull String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(@NotNull String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(@NotNull String employeePhone) {
        this.employeePhone = employeePhone;
    }

}
