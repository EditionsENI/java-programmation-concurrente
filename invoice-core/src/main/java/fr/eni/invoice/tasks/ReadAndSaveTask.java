package fr.eni.invoice.tasks;

import java.io.File;
import java.util.concurrent.Callable;

import fr.eni.invoice.datamodel.Invoice;
import fr.eni.invoice.services.data.InvoiceDataService;
import fr.eni.invoice.services.data.InvoiceJSONService;



/**
 * Tâche qui permet de lire depuis un fichier, puis de sauvegarder une Facture
 * @author tbrou
 *
 */
public class ReadAndSaveTask implements Callable<Invoice> {

	private File file;

	public ReadAndSaveTask(File file) {
		this.file = file;
	}
	
	@Override
	public Invoice call() throws Exception {
		Invoice facture = InvoiceJSONService.readFromFile(file);
		InvoiceDataService.getInstance().record(facture);
		return facture;
	}
	

}
