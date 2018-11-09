package dataModel;

public class Ingredient {
	
	private String name;
	private String category;
	private int quantity;
	private double price;
	
	public Ingredient(String name, int quantity) {
		this.name = name;
		//this.category = category;
		this.quantity = quantity;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPrice() {
		return this.price;
	}
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	



}
