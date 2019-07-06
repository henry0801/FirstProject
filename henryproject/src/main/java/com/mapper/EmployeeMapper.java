package com.mapper;

import java.util.List;

import com.dto.EmployeeDto;
import com.entity.Employee;

public interface EmployeeMapper {
    List<Employee> getAllEmployee();

    void updateEmployeeById(EmployeeDto eDto);

    void insertEmployee(EmployeeDto eDto);

    void deleteEmployeeById(EmployeeDto eDto);

    List<Employee> getUserWorksSatatus(String year,String month);

}
