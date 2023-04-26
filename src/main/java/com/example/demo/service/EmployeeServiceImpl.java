package com.example.demo.service;

import com.example.demo.observer.EmployeeObserver;
import com.example.demo.strategy.PayrollStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	private EmployeeServiceImpl() {}

	private List<EmployeeObserver> observers = new ArrayList<>();



	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}



	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if(optional.isPresent())
		{
			employee=optional.get();
		}
		else
		{
			throw new RuntimeException(" Employee not found for id :: "+id);
		}
		return employee;
	}




	@Override
	public void addObserver(EmployeeObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(EmployeeObserver observer) {
		observers.remove(observer);
	}

	private void notifyEmployeeAdded(Employee employee) {
		for (EmployeeObserver observer : observers) {
			observer.onEmployeeAdded(employee);
		}
	}

	private void notifyEmployeeUpdated(Employee employee) {
		for (EmployeeObserver observer : observers) {
			observer.onEmployeeUpdated(employee);
		}
	}

	private void notifyEmployeeDeleted(long employeeId) {
		for (EmployeeObserver observer : observers) {
			observer.onEmployeeDeleted(employeeId);
		}
	}

	@Override
	public void saveEmployee(Employee employee) {
		boolean isNew = employee.getId() == 0;
		this.employeeRepository.save(employee);
		if (isNew) {
			notifyEmployeeAdded(employee);
		} else {
			notifyEmployeeUpdated(employee);
		}
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);
		notifyEmployeeDeleted(id);
	}

	@Override
	public double generatePayroll(long employeeId, PayrollStrategy payrollStrategy, double bonus, double deduction) {
		Employee employee = getEmployeeById(employeeId);
		return payrollStrategy.calculate(employee.getSalary(), bonus, deduction);
	}

}