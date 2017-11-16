package p03_CarShopExtended.cars;

public class Seat implements Car, Sellable {
	
	private String model;
	private String color;
	private Integer horsePower;
	private String countryProduced;
	private Double price;

	public Seat(String model, String color, Integer horsePower, String countryProduced, Double price) {
		super();
		this.model = model;
		this.color = color;
		this.horsePower = horsePower;
		this.countryProduced = countryProduced;
		this.price = price;
	}

	public String getCountryProduced() {
		return countryProduced;
	}

	public Double getPrice() {
		return price;
	}

	@Override
	public String getModel() {
		return this.model;
	}

	@Override
	public String getColor() {
		return this.color;
	}

	@Override
	public Integer getHorsePower() {
		return this.horsePower;
	}

	@Override
	public String toString() {
		return String.format("This is %s produced in %s and have %d tires",
				this.getModel(),
				this.getCountryProduced(),
				Car.TIRES);
	}

}
