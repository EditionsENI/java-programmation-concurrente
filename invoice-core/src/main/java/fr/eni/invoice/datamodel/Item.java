package fr.eni.invoice.datamodel;

import java.math.BigDecimal;

public class Item {
	
	private String name;
	private BigDecimal unitPrice;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	
	
	

}
