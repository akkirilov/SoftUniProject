package app.models.customers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerBasicDto {

	@Expose
	@XmlAttribute(name = "name")
	private String name;
	
	@Expose
	@XmlElement(name = "birth-date")
	private String birthDate;
	
	@Expose
	@XmlElement(name = "is-young-driver")
	private Boolean isYoungDriver;

	public CustomerBasicDto() {
		super();
	}

	public CustomerBasicDto(String name, String birthDate, Boolean isYoungDriver) {
		super();
		this.name = name;
		this.birthDate = birthDate;
		this.isYoungDriver = isYoungDriver;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;			
	}

	public Boolean getIsYoungDriver() {
		return isYoungDriver;
	}

	public void setIsYoungDriver(Boolean isYoungDriver) {
		this.isYoungDriver = isYoungDriver;
	}
	
}
