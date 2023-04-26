package com.example.demo.controller;

import com.example.demo.observer.MasterLoggerObserver;
import com.example.demo.strategy.DefaultPayrollStrategy;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeController {

@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	//Display list of Employees
	@GetMapping("/")
	public String viewHomePage(Model model) {
		model.addAttribute("listEmployees", employeeService.getAllEmployees());
		return "index";
	}

	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		//create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}

	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		//save employee to DB
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
		//get employee from service
		Employee employee = employeeService.getEmployeeById(id);

		//set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);

		return "update_employee";
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") long id) {
		//call delete employee method
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}

	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("employee", new Employee());
		return "register";
	}

	@PostMapping("/register")
	public String registerEmployee(@Valid @ModelAttribute("employee") Employee employee,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		employee.setRole(Employee.Role.ROLE_USER);
		employeeService.saveEmployee(employee);
		return "redirect:/login";
	}

	@GetMapping("/generate-payroll")
	public String generatePayrollForm(Model model) {
		model.addAttribute("employeeId", "");
		model.addAttribute("bonus", "");
		model.addAttribute("deduction", "");
		return "generate-payroll";
	}

	@PostMapping("/generate-payroll")
	public String generatePayrollResult(@RequestParam("employeeId") long employeeId,
			@RequestParam("bonus") double bonus,
			@RequestParam("deduction") double deduction,
			Model model) {
		double payroll = employeeService.generatePayroll(employeeId, new DefaultPayrollStrategy(), bonus, deduction);

		model.addAttribute("payroll", payroll);
		return "payroll-result";
	}

	@PostConstruct
	public void init() {
		employeeService.addObserver(new MasterLoggerObserver());
	}
}