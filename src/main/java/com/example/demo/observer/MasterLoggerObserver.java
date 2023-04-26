package com.example.demo.observer;

import com.example.demo.model.Employee;
import java.io.FileWriter;
import java.io.IOException;
public class MasterLoggerObserver implements EmployeeObserver {
  @Override
  public void onEmployeeAdded(Employee employee) {
    System.out.println("Employee added with ID: " + employee);
    try {
      FileWriter myWriter = new FileWriter("C:/Users/fivet/Desktop/Employee Management System/MasterLogs.txt",true);
      myWriter.write("Employee added: " + employee.getId()); 
      myWriter.write(System.lineSeparator());
    
      myWriter.close();
      } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      }
  }

  @Override
  public void onEmployeeUpdated(Employee employee) {
    try {
      FileWriter myWriter = new FileWriter("C:/Users/fivet/Desktop/Employee Management System/MasterLogs.txt",true);
      myWriter.write("Employee updated with ID: " + employee.getId()); 
      myWriter.write(System.lineSeparator());
       
      myWriter.close();
      } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      }
  }

  @Override
  public void onEmployeeDeleted(long employeeId) {
    try {
      FileWriter myWriter = new FileWriter("C:/Users/fivet/Desktop/Employee Management System/MasterLogs.txt",true);
      myWriter.write("Employee deleted with ID: " + employeeId); 
      myWriter.write(System.lineSeparator());

      myWriter.close();
      } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      }
  }
}