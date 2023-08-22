package telran.employees.controller;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import telran.employees.dto.DepartmentSalary;
import telran.employees.dto.Employee;
import telran.employees.dto.SalaryDistribution;
import telran.employees.service.Company;
import telran.view.InputOutput;
import telran.view.Item;


public class CompanyController {
   private static final long MIN_ID = 100000;
private static final long MAX_ID = 999999;
private static final int MIN_SALARY = 6000;
private static final int MAX_SALARY = 50000;
private static final int MAX_AGE = 75;
private static final int MIN_AGE = 20;
static Company company;
	public static ArrayList<Item> getCompanyItems(Company company) {
		CompanyController.company = company;
		ArrayList<Item> res = new ArrayList<>(Arrays.asList(
				getItems()));
		return res;
	}
	private static Item[] getItems() {
		
		return new Item[] {
				Item.of("Add new Employee", CompanyController::addEmployeeItem),
				Item.of("Remove Employee", CompanyController::removeEmployeeItem),
				Item.of("All Employees", CompanyController::getEmployeesItem),
				Item.of("Data about Employee", CompanyController::getEmployeeItem),
				Item.of(" Employees by Salary", CompanyController::getEmployeesBySalaryItem),
				Item.of("Employees by Department", CompanyController::getEmployeesByDepartmentItem),
				Item.of("Update salary", CompanyController::updateSalaryItem),
				Item.of("Departments and Salary", CompanyController::getDepartmentSalaryDistributionItem),
				Item.of("Distribution by Salary", CompanyController::getSalaryDistributionItem),
				Item.of("Employees by Age", CompanyController::getEmployeesByAgeItem),
				Item.of("Update Department", CompanyController::updateDepartmentItem)
		};
	}
	static private Set<String> departments = new HashSet<>(Arrays.asList(new String[] {
			"QA", "Development", "Audit", "Management", "Accounting"
	}));
	static void addEmployeeItem(InputOutput io) {
		long id = io.readLong("Enter Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
		String name = io.readString("Enter name", "Wrong name",
				str -> str.matches("[A-Z][a-z]+"));
		String department = io.readString("Enter department", "Wrong department", departments );
		int salary = io.readInt("Enter salary", "Wrong salary", MIN_SALARY, MAX_SALARY);
		LocalDate birthDate = io.readDate("Enter birth data", "Wrong birth date entered",
				getBirthdate(MAX_AGE), getBirthdate(MIN_AGE));
		boolean res = company.addEmployee(new Employee(id, name, department, salary, birthDate));
		io.writeLine(res ? String.format("Employee with id %d has been added", id) : 
			String.format("Employee with id %d already exists", id));
	}
	private static LocalDate getBirthdate(int age) {
		
		return LocalDate.now().minusYears(age);
	}
	
	static void removeEmployeeItem(InputOutput io) {
	    long id = io.readLong("Enter Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
	    Employee removedEmployee = company.removeEmployee(id);
	    if (removedEmployee != null) {
	        io.writeLine("Employee removed:");
	        io.writeLine(removedEmployee.toString());
	    } else {
	        io.writeLine("Employee with the specified ID does not exist.");
	    }
	}

	static void getEmployeeItem(InputOutput io) {
	    long id = io.readLong("Enter Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
	    Employee employee = company.getEmployee(id);
	    if (employee != null) {
	        io.writeLine("Employee information:");
	        io.writeLine(employee.toString());
	    } else {
	        io.writeLine("Employee with the specified ID does not exist.");
	    }
	}

	static void getEmployeesItem(InputOutput io) {
	    List<Employee> employees = company.getEmployees();
	    io.writeLine("List of all employees:");
	    employees.forEach(io::writeLine);
	}
	
	static void getDepartmentSalaryDistributionItem(InputOutput io) {
		company.getDepartmentSalaryDistribution().forEach(io::writeLine);
		
	}
	
	static void getSalaryDistributionItem(InputOutput io) {
	    int interval = io.readInt("Enter salary interval", "Invalid interval", 1, Integer.MAX_VALUE);
	    List<SalaryDistribution> salaryDistribution = company.getSalaryDistribution(interval);
	    io.writeLine("Salary distribution:");
	    salaryDistribution.forEach(io::writeLine);
	}

	static void getEmployeesByDepartmentItem(InputOutput io) {
	    String department = io.readString("Enter department", "Wrong department", departments);
	    List<Employee> employees = company.getEmployeesByDepartment(department);
	    io.writeLine("Employees in the specified department:");
	    employees.forEach(io::writeLine);
	}

	static void getEmployeesBySalaryItem(InputOutput io) {
	    int salaryFrom = io.readInt("Enter minimum salary", "Invalid salary", MIN_SALARY, MAX_SALARY);
	    int salaryTo = io.readInt("Enter maximum salary", "Invalid salary", salaryFrom, MAX_SALARY);
	    List<Employee> employees = company.getEmployeesBySalary(salaryFrom, salaryTo);
	    io.writeLine("Employees in the specified salary range:");
	    employees.forEach(io::writeLine);
	}

	static void getEmployeesByAgeItem(InputOutput io) {
	    int ageFrom = io.readInt("Enter minimum age", "Invalid age", MIN_AGE, MAX_AGE);
	    int ageTo = io.readInt("Enter maximum age", "Invalid age", ageFrom, MAX_AGE);
	    List<Employee> employees = company.getEmployeesByAge(ageFrom, ageTo);
	    io.writeLine("Employees in the specified age range:");
	    employees.forEach(io::writeLine);
	}

	static void updateSalaryItem(InputOutput io) {
	    long id = io.readLong("Enter Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
	    Employee employee = company.getEmployee(id);
	    if (employee != null) {
	        int newSalary = io.readInt("Enter new salary", "Invalid salary", MIN_SALARY, MAX_SALARY);
	        Employee updatedEmployee = company.updateSalary(id, newSalary);
	        if (updatedEmployee != null) {
	            io.writeLine("Employee's salary has been updated:");
	            io.writeLine(updatedEmployee.toString());
	        } else {
	            io.writeLine("Failed to update employee's salary.");
	        }
	    } else {
	        io.writeLine("Employee with the specified ID does not exist.");
	    }
	}

	static void updateDepartmentItem(InputOutput io) {
	    long id = io.readLong("Enter Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
	    Employee employee = company.getEmployee(id);
	    if (employee != null) {
	        String newDepartment = io.readString("Enter new department", "Wrong department", departments);
	        Employee updatedEmployee = company.updateDepartment(id, newDepartment);
	        if (updatedEmployee != null) {
	            io.writeLine("Employee's department has been updated:");
	            io.writeLine(updatedEmployee.toString());
	        } else {
	            io.writeLine("Failed to update employee's department.");
	        }
	    } else {
	        io.writeLine("Employee with the specified ID does not exist.");
	    }
	}

	
	

}
