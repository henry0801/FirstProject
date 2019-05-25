package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dto.TestDto;
import com.form.TestForm;
import com.service.TestService;

@Controller
public class TestController {

	@Autowired
    private TestService testService;

    @RequestMapping(value = "/test/{userid}", method = RequestMethod.GET)
    public String test(Model model, @PathVariable int userid) {
        TestDto test = testService.getTest(userid);
        model.addAttribute("message", "MyBatisのサンプルです");
        model.addAttribute("test", test);
        return "test";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testAll(Model model) {
        List<TestDto> tests = testService.getTestAll();
        model.addAttribute("message", "MyBatisの全件取得サンプルです");
        model.addAttribute("tests", tests);
        return "testAll";
    }


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String showMessage(Model model) {
	    TestForm form = new TestForm();
	    form.setUserid(0);
	    form.setUsername("ここに名前を書いてね");
	    model.addAttribute("testForm", form);
	    model.addAttribute("message", "FORMの練習");
	    return "showMessage";
	}

	@RequestMapping(value = "/show", method = RequestMethod.POST)
	public String getFormInfo(@ModelAttribute TestForm form, Model model) {
	    model.addAttribute("message", "ID : " + form.getUserid() + " & name : " + form.getUsername());
	    return "showMessage";
	}
}