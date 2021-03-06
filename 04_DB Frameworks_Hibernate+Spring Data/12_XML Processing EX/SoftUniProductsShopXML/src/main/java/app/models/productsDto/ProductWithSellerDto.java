package app.models.productsDto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductWithSellerDto implements Serializable {

	private static final long serialVersionUID = -8236654919154788024L;

	@Expose
	@XmlAttribute(name = "name")
	private String name;
	
	@Expose
	@XmlAttribute(name = "price")
	private BigDecimal price;
	
	@Expose
	@SerializedName(value = "seller")
	@XmlAttribute(name = "seller")
	private String sellerName;

	public ProductWithSellerDto() {
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

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

}
