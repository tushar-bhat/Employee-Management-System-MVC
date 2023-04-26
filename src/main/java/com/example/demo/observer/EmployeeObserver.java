package com.example.demo.observer;

import com.example.demo.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeObserver {
  void onEmployeeAdded(Employee employee);
  void onEmployeeUpdated(Employee employee);
  void onEmployeeDeleted(long employeeId);
}
