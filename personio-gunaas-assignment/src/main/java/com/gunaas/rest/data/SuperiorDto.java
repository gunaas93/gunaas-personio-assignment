package com.gunaas.rest.data;

import java.util.List;

public class SuperiorDto {
    public List<EmployeeDto> employeeDtoList;

    public SuperiorDto(List<EmployeeDto> employeeDtoList) {
        this.employeeDtoList = employeeDtoList;
    }

    public List<EmployeeDto> getEmployeeDtoList() {
        return employeeDtoList;
    }

    public void setEmployeeDtoList(List<EmployeeDto> employeeDtoList) {
        this.employeeDtoList = employeeDtoList;
    }
}
