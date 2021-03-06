package app.models.bindingModels;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

public class ProductSoldJsonDto implements Serializable{

	private static final long serialVersionUID = 5268110618295611238L;

	@Expose
	private String name;
	
	@Expose
	private BigDecimal price;
	
	@Expose
	private String buyerFirstName;
	
	@Expose
	private String buyerLastName;

	public ProductSoldJsonDto() {
		super();
	}

	public ProductSoldJsonDto(String name, BigDecimal price, String buyerFirstName, String buyerLastName) {
		super();
		this.name = name;
		this.price = price;
		this.buyerFirstName = buyerFirstName;
		this.buyerLastName = buyerLastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getBuyerFirstName() {
		return buyerFirstName;
	}

	public void setBuyerFirstName(String buyerFirstName) {
		this.buyerFirstName = buyerFirstName;
	}

	public String getBuyerLastName() {
		return buyerLastName;
	}

	public void setBuyerLastName(String buyerLastName) {
		this.buyerLastName = buyerLastName;
	}
		
}
