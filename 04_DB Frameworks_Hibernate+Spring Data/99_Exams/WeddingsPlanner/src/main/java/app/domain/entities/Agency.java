package app.domain.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "agencies")
public class Agency implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Basic
	private String name;
	
	@Basic
	private Integer employeeCount;
	
	@Basic
	private String town;

	public Agency() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCapacity() {
		return employeeCount;
	}

	public void setCapacity(Integer employeeCount) {
		this.employeeCount = employeeCount;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}
	
}
