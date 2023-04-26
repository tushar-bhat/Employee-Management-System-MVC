package com.example.demo.service;

import com.example.demo.observer.EmployeeObserver;
import com.example.demo.strategy.PayrollStrategy;
import java.util.List;

import com.example.demo.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

	void addObserver(EmployeeObserver observer);
	void removeObserver(EmployeeObserver observer);
	List<Employee> getAllEmployees();
	void saveEmployee(Employee employee);
	Employee getEmployeeById(long id);
	void deleteEmployeeById(long id);
	double generatePayroll(long employeeId, PayrollStrategy payrollStrategy, double bonus, double deduction);
}


