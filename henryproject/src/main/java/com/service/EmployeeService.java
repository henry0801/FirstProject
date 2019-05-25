package com.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.EmployeeDto;
import com.entity.Employee;
import com.mapper.EmployeeMapper;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMappper;

    public List<EmployeeDto> getTestAll() {
        List<Employee> employeeList = employeeMappper.getTestAll();
        List<EmployeeDto> resultList = convertToDto(employeeList);
        return resultList;
    }

    public List<EmployeeDto> getUserWorksSatatus(String year,String month) {
        List<Employee> employeeList = employeeMappper.getUserWorksSatatus(year,month);
        List<EmployeeDto> resultList = convertToDto(employeeList);
        return resultList;
    }

    private List<EmployeeDto> convertToDto(List<Employee> employeeList) {
        List<EmployeeDto> resultList = new LinkedList<EmployeeDto>();
        for (Employee entity : employeeList) {
        	EmployeeDto dto = new EmployeeDto();
            BeanUtils.copyProperties(entity, dto);
            resultList.add(dto);
        }
        return resultList;
    }

}