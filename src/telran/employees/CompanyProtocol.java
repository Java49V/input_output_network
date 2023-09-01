package telran.employees;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import telran.employees.dto.DepartmentSalary;
import telran.employees.dto.Employee;
import telran.employees.dto.SalaryDistribution;
import telran.employees.dto.UpdateData;
import telran.employees.dto.FromTo;
import telran.employees.service.*;
import telran.employees.service.Company;
import telran.employees.service.CompanyImpl;
import telran.employees.service.CompanyNetProxy;
import telran.net.ApplProtocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements ApplProtocol {

	private Company company;

	public CompanyProtocol(Company company) {
		this.company = company;
	}

	@Override
	public Response getResponse(Request request) {
		Response response = null;
		String requestType = request.requestType();
		Serializable data = request.requestData();
		try {
			Serializable responseData = switch (requestType) {
			case "employee/add" -> employee_add(data);
			case "employee/remove" -> employee_remove(data);
			case "employee/get" -> employee_get(data);
			case "employees/get" -> employees_get(data);
			case "department/salary/distribution" -> department_salary_distribution(data);
			case "salary/distribution" -> salary_distribution(data);
			case "employees/department" -> employees_by_department(data);
			case "employees/salary" -> employees_by_salary(data);
			case "employees/age" -> employees_by_age(data);
			case "salary/update" -> salary_update(data);
			case "department/update" -> department_update(data);
			default -> new Response(ResponseCode.WRONG_TYPE, requestType + " is unsupported in the Company Protocol");
			};
			response = (responseData instanceof Response) ? (Response) responseData
					: new Response(ResponseCode.OK, responseData);

		} catch (Exception e) {
			response = new Response(ResponseCode.WRONG_DATA, e.toString());
		}
		return response;
	}

	private Serializable department_update(Serializable data) {

		@SuppressWarnings("unchecked")
		UpdateData<String> updateData = (UpdateData<String>) data;
		long id = updateData.id();
		String department = updateData.data();
		return company.updateDepartment(id, department);
	}

	Serializable employees_get(Serializable data) {

		return new ArrayList<>(company.getEmployees());
	}

	Serializable employee_get(Serializable data) {
		long id = (long) data;
		return company.getEmployee(id);
	}

	Serializable employee_add(Serializable data) {

		Employee empl = (Employee) data;
		return company.addEmployee(empl);
	}

	private Serializable employee_remove(Serializable data) {
		long id = (long) data;
		Employee removedEmployee = company.removeEmployee(id);
		return removedEmployee;
	}

	private Serializable department_salary_distribution(Serializable data) {
//        List<DepartmentSalary> departmentSalaries = company.getDepartmentSalaryDistribution();
//        return new ArrayList<>(departmentSalaries);
		return (Serializable) company.getDepartmentSalaryDistribution();
	}

	private Serializable salary_distribution(Serializable data) {
		int interval = (int) data;
//        List<SalaryDistribution> salaryDistributions = company.getSalaryDistribution(interval);
//        return new ArrayList<>(salaryDistributions);
		return (Serializable) company.getSalaryDistribution(interval);
	}

	private Serializable employees_by_department(Serializable data) {
		String department = (String) data;
//        List<Employee> employeesByDepartment = company.getEmployeesByDepartment(department);
//        return new ArrayList<>(employeesByDepartment);
		return (Serializable) company.getEmployeesByDepartment(department);
	}

	private Serializable employees_by_salary(Serializable data) {
		FromTo fromTo = (FromTo) data;
//        List<Employee> employeesBySalary = company.getEmployeesBySalary(fromTo.from(), fromTo.to());
//        return new ArrayList<>(employeesBySalary);
		return (Serializable) company.getEmployeesBySalary(fromTo.from(), fromTo.to());
	}

	private Serializable employees_by_age(Serializable data) {
		FromTo fromTo = (FromTo) data;
//        List<Employee> employeesByAge = company.getEmployeesByAge(fromTo.from(), fromTo.to());
//        return new ArrayList<>(employeesByAge);
		return (Serializable) company.getEmployeesByAge(fromTo.from(), fromTo.to());
	}

	private Serializable salary_update(Serializable data) {
		UpdateData<Integer> updateData = (UpdateData<Integer>) data;
		long id = updateData.id();
		int newSalary = updateData.data();
		Employee updatedEmployee = company.updateSalary(id, newSalary);
		return updatedEmployee;
	}

}
