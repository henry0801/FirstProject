package com.sec;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationControler {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLoginPage() {
		return "loginForm";
	}
}