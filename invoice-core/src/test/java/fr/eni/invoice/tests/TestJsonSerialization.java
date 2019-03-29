package fr.eni.invoice.tests;

import java.io.File;

import org.junit.Test;

import fr.eni.invoice.datamodel.Invoice;
import fr.eni.invoice.services.data.InvoiceJSONService;

public class TestJsonSerialization {
	
	
	
	
	@Test
	public void testJsonSerialization(){
		Invoice factureLue = InvoiceJSONService.readFromFile(new File("src/test/resources/bill.json"));
		System.out.println(factureLue.getCustomer().getName());
		
	}

}
