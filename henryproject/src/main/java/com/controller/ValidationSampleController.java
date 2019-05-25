package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entity.Employee;
import com.form.EmployeeForm;

@Controller
public class ValidationSampleController {

    private List<Employee> employeeList = new ArrayList<Employee>();

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("title", "社員一覧");
        model.addAttribute("message", "登録社員一覧情報を表示します");
        EmployeeForm form = new EmployeeForm();
        model.addAttribute("employeeListForm", form);
        model.addAttribute("employeeList", employeeList);
        return "employee";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public String list(@ModelAttribute EmployeeForm form, Model model, BindingResult result) {
    	Employee employee = new Employee();
        BeanUtils.copyProperties(form, employee);
        employeeList.add(employee);
        model.addAttribute("title", "社員一覧");
        model.addAttribute("message", form.getName() + "を登録しました。");
        model.addAttribute("employeeListForm", new EmployeeForm());
        model.addAttribute("employeeList", employeeList);
        return "employee";
    }
}