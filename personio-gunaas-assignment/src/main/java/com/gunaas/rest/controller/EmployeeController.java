package com.gunaas.rest.controller;

import com.gunaas.rest.data.EmployeeDto;
import com.gunaas.rest.data.SuperiorDto;
import com.gunaas.rest.service.EmployeeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Scope("request")
@RequestMapping(
        produces = {"application/json"},
        value = {"/employees"}
)
@Validated
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping({"/{name}/superiors"})
    @NotNull
    public ResponseEntity<List<EmployeeDto>> getSuperiors(@PathVariable("name") @NotBlank @NotNull String employeeName) {
        List<EmployeeDto> employeeDtoList = employeeService.getSuperiors(employeeName).stream()
                .map(e -> new EmployeeDto(e.getId(), e.getEmployeeName())).collect(Collectors.toList());
        return new ResponseEntity(new SuperiorDto(employeeDtoList), HttpStatus.OK);
    }
}
