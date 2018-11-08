package dataModel;

import java.util.ArrayList;
import java.util.List;

public class Order {
	
	private String invoiceNumber;
	private String customerEmail;
	private String status;
	private List<Burger> burgers = new ArrayList<Burger>();
	

	public Order(String invoiceNumber, String customerEmail, String status, ArrayList<Burger> burgers) {
		this.invoiceNumber = invoiceNumber;
		this.customerEmail = customerEmail;
		this.status = status;
		this.burgers = burgers;
	}


	public String getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public String getCustomerEmail() {
		return customerEmail;
	}


	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
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
