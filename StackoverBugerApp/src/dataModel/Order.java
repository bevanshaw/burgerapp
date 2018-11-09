package dataModel;

import java.util.ArrayList;
import java.util.List;

public class Order {
	
	private String invoiceNumber;
	private String customer;
	private String status;
	private List<Burger> burgers = new ArrayList<Burger>();
	

	public Order(String invoiceNumber, String customer, String status, List<Burger> burgers) {
		this.invoiceNumber = invoiceNumber;
		this.customer = customer;
		this.status = status;
		this.burgers = burgers;
	}


	public String getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public String getCustomer() {
		return customer;
	}


	public void setCustomer(String customerEmail) {
		this.customer = customerEmail;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public List<Burger> getBurgers() {
		return burgers;
	}


	public void setBurgers(List<Burger> burgers) {
		this.burgers = burgers;
	}

}
