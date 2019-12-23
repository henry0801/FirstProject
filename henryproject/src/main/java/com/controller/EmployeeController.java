package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dto.EmployeeDto;
import com.form.EmployeeForm;
import com.service.EmployeeService;

@Controller
@RequestMapping("/employee")
@SessionAttributes({"employees"})
public class EmployeeController {

	@Autowired
    private EmployeeService employeeService;

    //初期表示
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String show(Model model,ModelMap map) {

        return viewBuilder(new EmployeeForm(), model, map);
    }

    //再表示
    @RequestMapping(value = "", params = "refresh", method = RequestMethod.POST)
    public String viewBuilder(@ModelAttribute EmployeeForm employeeForm, Model model, ModelMap map) {
    	List<EmployeeDto> employees = employeeService.getAllEmployee();

        model.addAttribute("employees", employees);
        model.addAttribute(employeeForm);

        map.put("employees", employees);
        return "employee";
    }

    //保存ボタン
  	@RequestMapping(value = "", params = "save", method = RequestMethod.POST)
  	public String save(@ModelAttribute EmployeeForm employeeForm, Model model,ModelMap map) {

  		List<EmployeeDto> employees_before = (List<EmployeeDto>) map.get("employees");

  		List<EmployeeDto> employees_after = new ArrayList<>();

  		List<EmployeeDto> employees_update= new ArrayList<>();

		String[] userid = employeeForm.getUserid_exist();
		String[] username = employeeForm.getUsername_exist();
		String[] biko = employeeForm.getBiko_exist();
		String[] genba = employeeForm.getGenba_exist();
		String[] place = employeeForm.getPlace_exist();
		String[] delete_flg = employeeForm.getDelete_flg_exist();

		List<EmployeeDto> employees_new = new ArrayList<>();
		String[] userid_new = employeeForm.getUserid_new();
		String[] username_new = employeeForm.getUsername_new();
		String[] biko_new = employeeForm.getBiko_new();
		String[] genba_new = employeeForm.getGenba_new();
		String[] place_new = employeeForm.getPlace_new();
		String[] delete_flg_new = employeeForm.getDelete_flg_new();

		List<EmployeeDto> statistics_delete = new ArrayList<>();

  		if (userid!=null) {
  			for (int i = 0; i < userid.length; i++) {

  				EmployeeDto employeeDto = new EmployeeDto();

  				employeeDto.setUserid(userid[i]);
  				employeeDto.setUsername(username[i]);
  				employeeDto.setBiko(biko[i]);
  				employeeDto.setGenba(genba[i]);
  				employeeDto.setPlace(place[i]);

  				if ("0".equals(delete_flg[i])) {
  					employees_after.add(employeeDto);
				}else if ("1".equals(delete_flg[i])) {
					//pglk社員Pass
	  				if ("admin1".equals(userid[i])||"admin2".equals(userid[i])||"admin3".equals(userid[i])||"admin4".equals(userid[i])) {
						continue;
					}
					statistics_delete.add(employeeDto);
				}
  			}
  		}

		//セッション比較
		if (employees_before != null && employees_before.size() > 0) {
			for (int i = 0; i < employees_before.size(); i++) {
				EmployeeDto employee_before = employees_before.get(i);

				for (int j = 0; j < employees_after.size(); j++) {
					EmployeeDto employee_after = employees_after.get(j);

					if (employee_before.getUserid().equals(employee_after.getUserid())) {
						if (!employee_before.toString().equals(employee_after.toString())) {
							employees_update.add(employee_after);
						}
					}
				}
			}
		}

		if (delete_flg_new != null) {
			for (int i = 0; i < delete_flg_new.length; i++) {

				if ("1".equals(delete_flg_new[i]))
					continue;

				EmployeeDto employeeDto = new EmployeeDto();

				employeeDto.setUserid(userid_new[i]);
				employeeDto.setUsername(username_new.length > 0 ? username_new[i] : "");
				employeeDto.setBiko(biko_new.length > 0 ? biko_new[i] : "");
				employeeDto.setGenba(genba_new.length > 0 ? genba_new[i] : "");
				employeeDto.setPlace(place_new.length > 0 ? place_new[i] : "");

				employees_new.add(employeeDto);
			}
		}

		employeeService.deleteStatisticById(statistics_delete);
		employeeService.updateStatisticById(employees_update);
		employeeService.insertStatisticById(employees_new);

		return viewBuilder(employeeForm, model, map);
	}

    @RequestMapping(value = "", params = "return", method = RequestMethod.POST)
    public String toMenu(@ModelAttribute EmployeeForm form, Model model, BindingResult result) {
        return "menu";
    }
}