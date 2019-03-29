package fr.eni.invoice.datamodel;

import java.util.List;


/**
 * Une classe de modélisation permettant de représenter le concept de Facture
 * @author tbrou
 *
 */
public class Invoice {
	
	
	Integer id;
	
	Customer customer;
	List<InvoiceLine> lines;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<InvoiceLine> getLines() {
		return lines;
	}
	public void setLignes(List<InvoiceLine> lines) {
		this.lines = lines;
	}
	
	
	
	

}
