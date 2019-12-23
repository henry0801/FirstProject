package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.service.EmployeeService;

@Controller
@SessionAttributes(value={"username"})
public class IndexController {

	Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
    private EmployeeService employeeService;

	@RequestMapping(value = {"/", "/menu"}, method = RequestMethod.GET)
	public String index(ModelMap model) {


		/*try {
			employeeService.getAllEmployee();
		} catch (MyBatisSystemException e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			return "error";
		}*/

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username

		model.addAttribute("username", name);

		return "menu";
	}

}