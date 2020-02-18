package com.gunaas.rest.data;

public class EmployeeDto {

    private Long employeeId;

    private String employeeName;

    public EmployeeDto(long id, String employeeName) {
        this.employeeId = id;
        this.employeeName = employeeName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
