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

        String workYear = workStatusForm.getWorkYear();
        String workMonth = workStatusForm.getWorkMonth();

        if (workYear == null || workMonth == null) {
           Calendar cal = Calendar.getInstance();
           workYear = String.valueOf(cal.get(1));
           workMonth = String.format("%02d", cal.get(2) + 1);
        }

        workStatusForm.setWorkYear(workYear);
        workStatusForm.setWorkMonth(workMonth);

    	return showlist(workStatusForm, request,model,map);
    }

    //戻るボタン
    @RequestMapping(value = "", params = "return", method = RequestMethod.POST)
    public String toMenu(@ModelAttribute WorkStatusForm workStatusForm, Model model, ModelMap map) {

        return "menu";
    }

    @RequestMapping(value = "", params = "search", method = RequestMethod.POST)
    public String showlist(@ModelAttribute WorkStatusForm workStatusForm, HttpServletRequest request, Model model,ModelMap map) {

    	String workYear = workStatusForm.getWorkYear();
    	String workMonth =  workStatusForm.getWorkMonth();

    	List<EmployeeDto> employees = employeeService.getUserWorksSatatus(workYear,workMonth);
        model.addAttribute("workStatusForm", workStatusForm);
        model.addAttribute("employees", employees);

        map.put("year", workYear);
        map.put("month", workMonth);
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