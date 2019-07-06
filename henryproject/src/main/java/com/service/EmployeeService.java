package com.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.EmployeeDto;
import com.entity.Employee;
import com.mapper.EmployeeMapper;

@Service
public class EmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeMapper employeeMappper;


    private List<EmployeeDto> convertToDto(List<Employee> employeeList) {
        List<EmployeeDto> resultList = new LinkedList<EmployeeDto>();
        for (Employee entity : employeeList) {
        	EmployeeDto dto = new EmployeeDto();
            BeanUtils.copyProperties(entity, dto);
            resultList.add(dto);
        }
        return resultList;
    }

    public List<EmployeeDto> getAllEmployee() {
        List<Employee> employeeList = employeeMappper.getAllEmployee();
        List<EmployeeDto> resultList = convertToDto(employeeList);
        return resultList;
    }
    public void updateStatisticById(List<EmployeeDto> employee) {
    	for (EmployeeDto dto : employee) {
    		employeeMappper.updateEmployeeById(dto);
    		logger.info("Update The Employee Over!");
        }
    }

    public void insertStatisticById(List<EmployeeDto> employee) {
    	for (EmployeeDto dto : employee) {
    		employeeMappper.insertEmployee(dto);
    		logger.info("INSERT The Employee Over!");
        }
    }

    public void deleteStatisticById(List<EmployeeDto> employee) {
    	for (EmployeeDto dto : employee) {
    		employeeMappper.deleteEmployeeById(dto);
    		logger.info("DELETE The Employee Over!");
        }
    }

    public List<EmployeeDto> getUserWorksSatatus(String year,String month) {
        List<Employee> employeeList = employeeMappper.getUserWorksSatatus(year,month);
        List<EmployeeDto> resultList = convertToDto(employeeList);
        return resultList;
    }

}