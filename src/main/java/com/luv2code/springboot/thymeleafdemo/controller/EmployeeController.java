package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {


	@Autowired
	private EmployeeService employeeService;
	public EmployeeController() {
      //controller
	}

	private static final String PATH_FOR_LIST="employees/list-employees";
	private static final String REDIRECT="redirect:";
	private static final String EMPFORM="employees/employee-form";
	private static final String LIST="/employees/list";



	@GetMapping("/list")
	public String listEmployees(Model theModel) {

		// get employees from db
		List<Employee> theEmployees = employeeService.findAll();

		// add to the spring model
		theModel.addAttribute("employees", theEmployees);

		return PATH_FOR_LIST;
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Employee theEmployee = new Employee();
		
		theModel.addAttribute("employee", theEmployee);
		
		return EMPFORM;
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId,
									Model theModel) {
		
		// get the employee from the service
		Employee theEmployee = employeeService.findById(theId);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);
		
		// send over to our form
		return EMPFORM;
	}

	@PostMapping("/save")
	public String saveEmployee(@Valid @ModelAttribute("employee") Employee theEmployee, BindingResult bindingResult) {

		if(bindingResult.hasErrors()){

			return EMPFORM;
		}
		else {

			employeeService.save(theEmployee);
			return REDIRECT+LIST;
		}

	}

	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		
		// delete the employee
		employeeService.deleteById(theId);
		
		// redirect to /employees/list
		return REDIRECT+LIST;
		
	}
	
	
	@GetMapping("/search")
	public String search(@RequestParam("firstName") String theFirstName,
						 @RequestParam("lastName") String theLastName,
						 Model theModel) {
		
		// check names, if both are empty then just give list of all employees

		if (theFirstName.trim().isEmpty() && theLastName.trim().isEmpty()) {
			return REDIRECT+LIST;
		}
		else {
			// else, search by first name and last name
			List<Employee> theEmployees =
							employeeService.searchBy(theFirstName, theLastName);
			
			// add to the spring model
			theModel.addAttribute("employees", theEmployees);
			
			// send to list-employees
			return PATH_FOR_LIST;
		}
		
	}

}


















