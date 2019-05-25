package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.form.LoginForm;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String tologinpage() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("LoginForm")LoginForm loginform,  ModelMap model) {

		String userid = loginform.getUserid();
		String password = loginform.getPassword();

		if (!"henry".equals(userid) || !"12345".equals(password)) {
			return "login";
		}

		return "menu";
	}
}