package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	// load employee data
	private EmployeeService employeeService;

	// add mapping for "/list"
	@Autowired
	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}

	@GetMapping("/list")
	public String listEmployees(Model theModel) {

		// get the employees from database via employeeService
		// List<Employee> theEmployees =  employeeService.findAll();
		List<Employee> theEmployees = employeeService.findAllByOrderByLastNameAsc();
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);

		return "employees/list-employees";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		// create model attribute to bind form data
		Employee newEmployee = new Employee();

		theModel.addAttribute("employee", newEmployee);

		// return the template in src/main/resources/templates/employees/employee-form
		return "employees/employee-form";
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {

		// save the employee
		employeeService.save(theEmployee);

		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
		Employee theEmployee = employeeService.findById(theId);

		theModel.addAttribute("employee", theEmployee);

		return "employees/employee-form";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId")int theId) {
		employeeService.deleteById(theId);

		return "redirect:/employees/list";
	}

	/*
	* The reason we are not using @DeleteMapping is that html does only support GET and POST methods
	* JS supports DELETE methods so normally you should you @DeleteMapping
	@DeleteMapping("/delete")
	public String deleteEmployee(@RequestParam("employeeId") int employeeId) {
		employeeService.deleteById(employeeId);

		return "redirect:/employees/list";
	}

	 */

}









