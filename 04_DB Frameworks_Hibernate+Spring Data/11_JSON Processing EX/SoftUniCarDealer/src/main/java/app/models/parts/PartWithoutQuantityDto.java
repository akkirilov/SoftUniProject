package app.models.parts;

import java.math.BigDecimal;


import com.google.gson.annotations.Expose;

public class PartWithoutQuantityDto {

	@Expose
	private String name;
	
	@Expose
	private BigDecimal price;
	
	
	public PartWithoutQuantityDto() {
		super();
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

}
