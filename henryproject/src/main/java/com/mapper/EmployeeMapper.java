package com.mapper;

import java.util.List;

import com.entity.Employee;

public interface EmployeeMapper {
    List<Employee> getTestAll();

    List<Employee> getUserWorksSatatus(String year,String month);

}
