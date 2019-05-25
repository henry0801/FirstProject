package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dto.EmployeeDto;
import com.entity.Employee;
import com.form.EmployeeForm;
import com.service.EmployeeService;

@Controller
@RequestMapping("showEmployee")
public class EmployeeController {

	@Autowired
    private EmployeeService employeeService;
    private List<Employee> employeeList = new ArrayList<Employee>();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("employeeList", employeeList);
        return "employee";
    }

    @RequestMapping(value = "showEmployeeForm", params = "search", method = RequestMethod.POST)
    public String showlist(@ModelAttribute EmployeeForm form, Model model, BindingResult result) {
    	List<EmployeeDto> employees = employeeService.getTestAll();
        model.addAttribute("employeeListForm", new EmployeeForm());
        model.addAttribute("employeeList", employees);
        return "employee";
    }


    @RequestMapping(value = "showEmployeeForm", params = "return", method = RequestMethod.POST)
    public String toMenu(@ModelAttribute EmployeeForm form, Model model, BindingResult result) {
        return "menu";
    }
}