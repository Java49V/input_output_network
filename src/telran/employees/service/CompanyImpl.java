package telran.employees.service;

import java.util.stream.Collectors;

import telran.employees.dto.DepartmentSalary;
import telran.employees.dto.Employee;
import telran.employees.dto.SalaryDistribution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class CompanyImpl implements Company {
  LinkedHashMap<Long, Employee> employees = new LinkedHashMap<>();
	@Override
	public boolean addEmployee(Employee empl) {
		
		return employees.putIfAbsent(empl.id(), empl) == null;
	}

	@Override
	public Employee removeEmployee(long id) {
		
		return employees.remove(id);
	}

	@Override
	public Employee getEmployee(long id) {
		
		return employees.get(id);
	}

	@Override
	public List<Employee> getEmployees() {
		
		return new ArrayList<>(employees.values());
	}

	@Override
	public List<DepartmentSalary> getDepartmentSalaryDistribution() {
		
		return employees.values().stream()
				.collect(Collectors.groupingBy(Employee::department,
						Collectors.averagingInt(Employee::salary)))
				.entrySet().stream().map(e -> new DepartmentSalary(e.getKey(),
						e.getValue())).toList();
	}
	
	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {
	    List<SalaryDistribution> result = new ArrayList<>();
	    List<Employee> employeeList = new ArrayList<>(employees.values());

	    int minSalary = employeeList.stream()
	            .mapToInt(Employee::salary)
	            .min()
	            .orElse(0);
	    int maxSalary = employeeList.stream()
	            .mapToInt(Employee::salary)
	            .max()
	            .orElse(0);

	    int range = maxSalary - minSalary;
	    int intervals = range / interval + 1;

	    for (int i = 0; i < intervals; i++) {
	        int lowerBound = minSalary + i * interval;
	        int upperBound = Math.min(minSalary + (i + 1) * interval - 1, maxSalary);

	        long amountEmployeesInInterval = employeeList.stream()
	                .filter(employee -> employee.salary() >= lowerBound && employee.salary() <= upperBound)
	                .count();

	        result.add(new SalaryDistribution(lowerBound, upperBound, (int) amountEmployeesInInterval));
	    }

	    return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void restore(String filePath) {
	    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
	        employees = (LinkedHashMap<Long, Employee>) inputStream.readObject();
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void save(String filePath) {
	    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
	        outputStream.writeObject(employees);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
