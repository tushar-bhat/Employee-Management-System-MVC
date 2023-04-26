package com.example.demo.strategy;

public class DefaultPayrollStrategy implements PayrollStrategy {
  @Override
  public double calculate(double salary, double bonus, double deduction) {
    return salary + bonus - deduction;
  }
}
