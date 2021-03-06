package softUni;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.hibernate.internal.util.compare.CalendarComparator;

import com.sun.javafx.collections.MappingChange.Map;
import com.sun.jmx.snmp.Timestamp;

import entities.Address;
import entities.Department;
import entities.Employee;
import entities.Project;
import entities.Town;

public final class Problem {	
	
	public static void addObjects(EntityManager em) {
				
		em.getTransaction().begin();
		
		Town town = new Town();
		town.setName("Burgas");
		em.persist(town);
		
		Address address1 = new Address();
		address1.setText("55 Morska");
		address1.setTown(town);
		em.persist(address1);
		
		Address address2 = new Address();
		address2.setText("55 Cherna");
		address2.setTown(town);
		em.persist(address2);
		
		Address address3 = new Address();
		address3.setText("55 Ribna");
		address3.setTown(town);
		em.persist(address3);
		
		Employee manager = em.find(Employee.class, 1);
		
		Department department = new Department();
		department.setName("Training");
		department.setManager(manager);
		em.persist(department);
		
		Employee emp1 = new Employee("Gosho", "Goshov", "Peshov", "Trainer", department, manager, new BigDecimal(54000.00), address1);
		em.persist(emp1);
		Employee emp2 = new Employee("Pesho", "Peshov", "Goshov", "Trainer", department, manager, new BigDecimal(55000.00), address1);
		em.persist(emp2);
		Employee emp3 = new Employee("Raicho", "Goshov", "Rachkav", "Trainer", department, manager, new BigDecimal(56000.00), address1);
		em.persist(emp3);
		Employee emp4 = new Employee("Marcho", "Goshov", "Marchov", "Trainer", department, manager, new BigDecimal(57000.00), address1);
		em.persist(emp4);
		Employee emp5 = new Employee("Rachka", "Rachkava", "Peshov", "Trainer", department, manager, new BigDecimal(58000.00), address1);
		em.persist(emp5);
		
		em.getTransaction().commit();
		em.clear();
			
	}
	
	public static void removeObjects(EntityManager em) {
		
		List<Town> towns = em.createQuery("SELECT t FROM Town AS t").getResultList();
		
		for (Town town : towns) {
			if (town.getName().length() > 5) {
				em.detach(town);
			} 
		}
		
		em.getTransaction().begin();
		for (Town town : towns) {
			if (em.contains(town)) {
				System.out.println("To persist: " + town.getName());
				town.setName(town.getName().toLowerCase());
				em.persist(town);
			}
			
		}
		
		em.getTransaction().commit();
		em.clear();
				
	}
	
	public static void isEmployeeExist(EntityManager em, String name) {
		
		List<Employee> employees = em.createQuery("SELECT e FROM Employee AS e").getResultList();
		
		boolean found = false;
		
		for (Employee employee : employees) {
			if ((employee.getFirstName() + " " + employee.getLastName()).equals(name)) {
				System.out.println("Yes");
				return;
			}
		}
		
		if (!found) {
			System.out.println("No");
		}
		
		em.clear();
		
	}
	
	public static void addTown(EntityManager em, String name) {
		
		Town town = new Town();
		town.setName(name);
		
		em.getTransaction().begin();
		em.persist(town);
		em.getTransaction().commit();
		em.clear();
				
	}

	public static void refreshEntityManager(EntityManager em, Integer id) {
		
		Employee employee = em.find(Employee.class, id);
		System.out.println("Before: " + employee.getFirstName());
		
		employee.setFirstName(employee.getFirstName().toUpperCase());
		em.getTransaction().begin();
		em.persist(employee);
		
		em.refresh(employee);
		em.getTransaction().commit();
		
		employee = em.find(Employee.class, id);
		System.out.println("After: " + employee.getFirstName());
		
		em.clear();
		
	}

	public static void findEmployeesWithSalaryOverEntered(EntityManager em, BigDecimal enteredSalary) {
		
		List<String> employees = em.createQuery("SELECT e.firstName FROM Employee AS e WHERE e.salary > " + enteredSalary).getResultList();
		if (employees == null || employees.size() < 1) {
			System.out.println("There are no employees with salary over " + enteredSalary);
			em.clear();
			return;
		}
		
		System.out.println("Employees with salary over " + enteredSalary + ":");
		for (String employee : employees) {
			System.out.println(employee);
		}
		
		em.clear();
		
	}

	public static void findEmployeesByTown(EntityManager em, String nameOfTown) {

		List<Employee> employees = em.createQuery("SELECT e FROM Employee AS e WHERE e.address.town.name = '" + nameOfTown + "'").getResultList();
		if (employees == null || employees.size() < 1) {
			System.out.println("There are no employees from " + nameOfTown);
			em.clear();
			return;
		}
		
		Integer counter = 1;
		for (Employee employee : employees) {
			System.out.println(counter++ + ". " + employee.getFirstName() + " " + employee.getLastName() + 
					", Town: " + nameOfTown + ", Address: " + employee.getAddress().getText() + ";");
		}
		
		em.clear();
		
	}

	public static void findEmployeesByDepartment(EntityManager em, String nameOfDepartment) {
		
		List<Employee> employees = em.createQuery("SELECT e FROM Employee AS e WHERE e.department.name = '" + nameOfDepartment + "'").getResultList();
		if (employees == null || employees.size() < 1) {
			System.out.println("There are no department " + nameOfDepartment);
			em.clear();
			return;
		}
		
		employees = employees
				.stream()
				.sorted((a, b) -> a.getSalary().compareTo(b.getSalary()))
				.collect(Collectors.toList());
		
		Integer counter = 1;
		for (Employee employee : employees) {
			System.out.println(counter++ + ". " + employee.getFirstName() + " " + employee.getLastName() + 
					", Department: " + nameOfDepartment + ", Salary: " + employee.getSalary() + ";");
		}
		
		em.clear();
		
	}

	public static void addNewAddress(EntityManager em, String addressToAdd, String fromTown) {
		
		Town town = null;
		
		try {
			town = (Town) em.createQuery("SELECT t FROM Town AS t WHERE t.name = '" + fromTown + "'").getSingleResult();
		} catch (Exception e) {
			System.out.println("There are no such town");
			System.out.println("If you want to add this town? (y/n)");
			
			Scanner scanner = new Scanner(System.in);
			String res = scanner.nextLine();
			
			if (res.equals("y")) {
				addTown(em, fromTown);
				System.out.println("Town " + fromTown + " added!");
				town = (Town) em.createQuery("SELECT t FROM Town AS t WHERE t.name = '" + fromTown + "'").getSingleResult();
			} else {
				return;
			}
		}
		
		Address newAddress = new Address();
		newAddress.setTown(town);
		newAddress.setText(addressToAdd);
		
		em.getTransaction().begin();
		em.persist(newAddress);
		System.out.println("Address " + addressToAdd + " added!");
		em.getTransaction().commit();
		em.clear();
				
	}

	public static void updateEmployeeAddress(EntityManager em, String newAddressToAdd, String newTownToAdd, Integer employeeId) {

		Employee employee = null;
		try {
			employee = (Employee) em.createQuery("SELECT e FROM Employee AS e WHERE e.id = " + employeeId).getSingleResult();
		} catch (Exception e) {
			System.out.println("There are no employee with id " + employeeId);
			return;
		}
		
		addNewAddress(em, newAddressToAdd, newTownToAdd);
		
		Address address = null;
		try {
			address = (Address) em.createQuery("SELECT a FROM Address AS a WHERE a.text = '" 
		+ newAddressToAdd + "' AND a.town.name = '" + newTownToAdd + "'").getSingleResult();
		} catch (Exception e) {
			return;
		}
		
		employee = (Employee) em.createQuery("SELECT e FROM Employee AS e WHERE e.id = " + employeeId).getSingleResult();
		employee.setAddress(address);
		
		em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();
		em.clear();
		
		System.out.println(employee.getFirstName() + " " + employee.getLastName() + " have new address " + newAddressToAdd + ", " + newTownToAdd);
		
	}

	public static void findTopTenAddresses(EntityManager em) {
		
		List<Address> addresses = em.createQuery("SELECT a FROM Address AS a").getResultList();
		addresses = addresses
				.stream()
				.sorted((a, b) -> {
					int res = Integer.compare(b.getEmployees().size(), a.getEmployees().size());
					if (res == 0) {
						res = a.getTown().getName().compareTo(b.getTown().getName());
					}
					return res;
				})
				.limit(10)
				.collect(Collectors.toList());
		
		for (Address address : addresses) {
			System.out.printf("%s, %s - %d employees%n", address.getText(), address.getTown().getName(), address.getEmployees().size());
		}
		
		em.clear();
		
	}
	
	public Integer countEmployeesOnGivenAddress(Address address) {
		
		return 0;
	}

	public static void findEmployeesProjectsById(EntityManager em, Integer id) {
		
		Employee employee = null;
		try {
			employee = (Employee) em.createQuery("SELECT e FROM Employee AS e WHERE id = " + id).getSingleResult();
		} catch (Exception e) {
			System.out.println("There are no employee with id " + id);
			em.clear();
			return;
		}
		
		System.out.println("Employee: " + employee.getFirstName() + " " + employee.getLastName() + ", " + employee.getJobTitle());
		if (employee.getProjects().size() < 1) {
			System.out.println("No projects");
			em.clear();
			return;
		}
		
		List<Project> projects = new ArrayList<Project>();
		for (Project p : employee.getProjects()) {
			projects.add(p);
		}
		projects = projects
				.stream()
				.sorted((a, b) -> a.getName().compareTo(b.getName()))
				.collect(Collectors.toList());

		Integer counter = 1;
		System.out.println("Projects: ");
		for (Project p : projects) {
			System.out.println(counter++ + ". " + p.getName() + ";");
		}
		
	}

	public static void findEmployeesWithProjectsByDateInterval(EntityManager em, Integer startYear, Integer endYear) {

		String sql = "SELECT e FROM Employee AS e";
		List<Employee> employees = em.createQuery(sql).getResultList();
		
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < employees.size(); i++) {
			for (Project p : employees.get(i).getProjects()) {
				calendar.setTime(p.getStartDate());
				if (calendar.get(Calendar.YEAR) < startYear || calendar.get(Calendar.YEAR) > endYear) {
					employees.remove(employees.get(i));
					i--;
				}
				calendar.clear();
			}
		}
		
		Integer counter = 1;
		for (Employee employee : employees) {
			System.out.println(counter++ + ". " + employee.getFirstName() + " " + employee.getLastName() + ", " 
					+ employee.getManager().getFirstName() + " " + employee.getManager().getFirstName());
			for (Project p : employee.getProjects()) {
				System.out.printf("-- %s start date: %s", p.getName(), p.getStartDate() );
				if (p.getEndDate() != null && !p.getEndDate().equals("")) {
					System.out.printf(", end date %s%n", p.getEndDate());
				} else {
					System.out.println();
				}
			}
		}
		
	}

	public static void findDepartmentsByMinCountOfEmployees(EntityManager em, Integer minCount) {

		List<Department> departments = em.createQuery("SELECT d FROM Department AS d").getResultList();
		departments = departments
				.stream()
				.filter(a -> a.getEmployees().size() >= minCount)
				.sorted((a, b) -> {
					int res = Integer.compare(b.getEmployees().size(), a.getEmployees().size());
					if (res == 0) {
						res = a.getName().compareTo(b.getName());
					}
					return res;
				})
				.collect(Collectors.toList());
		
		Integer counter = 1;
		for (Department department : departments) {
			System.out.println(counter++ + ". " + department.getName() + " - " + department.getEmployees().size() + " employees;");
		}
		
	}

	public static void tryConcurentChangesToDatabase() {
		
		EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("softuni");
		EntityManager em1 = emf1.createEntityManager();
		EntityManager em2 = emf1.createEntityManager();
		
		em1.getTransaction().begin();
		Employee employee1 = em1.find(Employee.class, 1);
		System.out.println("First name before c1: " + employee1.getFirstName());
		employee1.setFirstName("gogo11111111");
		
		em2.getTransaction().begin();
		Employee employee2 = em2.find(Employee.class, 1);
		System.out.println("First name before c2: " + employee2.getFirstName());
		employee2.setFirstName("gogo2222222");
		
		em1.persist(employee1);
		em2.persist(employee2);
		em1.getTransaction().commit();
		em2.getTransaction().commit();
				
		employee1 = em1.find(Employee.class, 1);
		System.out.println("First name after c1: " + employee1.getFirstName());
		employee2 = em2.find(Employee.class, 1);
		System.out.println("First name after c2: " + employee2.getFirstName());
		
		EntityManager em3 = emf1.createEntityManager();
		Employee employee3 = em3.find(Employee.class, 1);
		System.out.println("First name check: " + employee3.getFirstName());
		
		em1.close();
		em2.close();
		em3.close();
		emf1.close();
		
	}

	public static void findLatestTenProjects(EntityManager em) {
		
		List<Project> projects = em.createQuery("SELECT p FROM Project AS p").getResultList();
		
		projects = projects
				.stream()
				.sorted((a, b) -> b.getStartDate().compareTo(a.getStartDate()))
				.limit(10)
				.sorted((a, b) -> a.getName().compareTo(b.getName()))
				.collect(Collectors.toList());
		
		for (Project project : projects) {
			System.out.printf("%s (%s), from %s to %s%n", project.getName(), project.getDescription(), project.getStartDate(), project.getEndDate());
		}
		
	}

	public static void increaseSalaryInDepartment(EntityManager em, String departmentName) {
		
		Department d = null;
		try {
			d = (Department) em.createQuery("SELECT d FROM Department AS d WHERE d.name = '" + departmentName + "';").getSingleResult();
		} catch (Exception e) {
			em.clear();
			System.out.println("There are no such department!");
			return;
		}
		
		em.getTransaction().begin();
		Query query = em.createQuery("SELECT e FROM Employee AS e WHERE e.department.name = :departmentName");
		query.setParameter("departmentName", departmentName);
		
		List<Employee> employees = query.getResultList();
		for (Employee employee : employees) {
			employee.setSalary(employee.getSalary().multiply(new BigDecimal("1.12")));
			em.persist(employee);
		}
		em.getTransaction().commit();
		em.clear();

		System.out.println("Salaries updated with 12%!");
		
	}

	public static void removeTown(EntityManager em, String townName) {
		
		Town town = null;
		try {
			town = (Town) em.createQuery("SELECT t FROM Town AS t WHERE t.name = '" + townName + "'").getSingleResult();
		} catch (Exception e) {
			em.clear();
			System.out.println("There are no such Town!");
			return;
		}
		
		em.clear();
		em.getTransaction().begin();
		
		List<Employee> employees = em.createQuery("SELECT e FROM Employee AS e WHERE e.address.town.name = '" + townName + "'").getResultList();
		for (Employee employee : employees) {
			if (employee.getAddress().getTown().getName().equals(townName)) {
				employee.setAddress(null);
				em.persist(employee);
			}
		}
		
		Integer counter = 0;
		List<Address> addresses = em.createQuery("SELECT a FROM Address AS a WHERE a.town.name = '" + townName + "'").getResultList();
		for (Address address : addresses) {
			em.remove(address);
			counter++;
		}

		town = (Town) em.createQuery("SELECT t FROM Town AS t WHERE t.name = '" + townName + "'").getSingleResult();
		em.remove(town);
		
		em.getTransaction().commit();
		em.clear();
		
		System.out.println("Town " + townName + " removed! " + counter + " addresses removed!");
		
	}

	public static void findEnmployeeByFirstLetters(EntityManager em, String employeeName) {
		
		Integer counter = 1;
		List<Employee> employees = em.createQuery("SELECT e FROM Employee AS e WHERE e.firstName LIKE '" + employeeName + "%'").getResultList();
		if (employees.size() < 1) {
			System.out.println("Nothing found!");
			em.clear();
			return;
		}
		
		for (Employee employee : employees) {
			System.out.println(counter++ + ". " + employee.getFirstName() + " " + employee.getLastName());
		}
		
	}

	public static void findMaxSalariesForAllDepartments(EntityManager em) {
		
		Integer counter = 1;
		List<Employee> employees = em.createQuery("SELECT e FROM Employee AS e GROUP BY e.department.name").getResultList();
		employees = employees
				.stream()
				.sorted((a, b) -> {
					int res = b.getSalary().compareTo(a.getSalary());
					if (res == 0) {
						res = a.getDepartment().getName().compareTo(b.getDepartment().getName());
					}
					return res;
				})
				.collect(Collectors.toList());
		
		for (Employee employee : employees) {
			System.out.println(counter++ + ". " 
					+ employee.getDepartment().getName() + " "
					+ employee.getSalary()
					+ " (" + employee.getFirstName() + " " + employee.getLastName() 
					+ ", " + employee.getJobTitle()	+ ")");
		}
		
		em.clear();
		
	}

}
