package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {

	Logger logger = LoggerFactory.getLogger(LogoutController.class);

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {

		return "loginForm";
	}

}