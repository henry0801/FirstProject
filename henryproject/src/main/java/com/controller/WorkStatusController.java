package com.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dto.EmployeeDto;
import com.form.WorkStatusForm;
import com.service.EmployeeService;

@Controller
@RequestMapping("workStatus")
@SessionAttributes(value={"employees","year","month","message"})
public class WorkStatusController {

	@Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(@ModelAttribute WorkStatusForm workStatusForm, HttpServletRequest request, Model model,ModelMap map) {

    	Calendar cal = Calendar.getInstance();
    	String year = String.valueOf(cal.get(Calendar.YEAR));
    	String month = String.format("%02d", cal.get(Calendar.MONTH)+1);

    	workStatusForm.setWorkYear(year);
    	workStatusForm.setWorkMonth(month);

    	map.put("year", year);
    	map.put("month", month);

    	return showlist(workStatusForm, request,model,map);
    }

    @RequestMapping(value = "", params = "search", method = RequestMethod.POST)
    public String showlist(@ModelAttribute WorkStatusForm workStatusForm, HttpServletRequest request, Model model,ModelMap map) {

    	String workYear = workStatusForm.getWorkYear();
    	String workMonth =  workStatusForm.getWorkMonth();

    	List<EmployeeDto> employees = employeeService.getUserWorksSatatus(workYear,workMonth);
        model.addAttribute("workStatusForm", workStatusForm);
        model.addAttribute("employees", employees);

        map.put("employees", employees);

        return "workStatus";
    }

    @RequestMapping(value = "", params = "test1", method = RequestMethod.POST)
    public String test1(@ModelAttribute WorkStatusForm workStatusForm, HttpServletRequest request, Model model,ModelMap map) {


    	String message = "test1";

    	map.put("message", message);

        return "workStatus";
    }

    @RequestMapping(value = "", params = "test2", method = RequestMethod.POST)
    public String test2(@ModelAttribute WorkStatusForm workStatusForm, HttpServletRequest request, Model model,ModelMap map) {


    	String message = "test2";

    	map.put("message", message);

        return "workStatus";
    }

}