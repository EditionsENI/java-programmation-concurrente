package fr.eni.invoice.services.data;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.eni.invoice.datamodel.Invoice;
import fr.eni.invoice.services.logging.Logger;
import fr.eni.invoice.services.logging.LoggerFactory;

/**
 * Service pour les opérations de sérialisation / désérialisation
 * @author tbrou
 *
 */
public class InvoiceJSONService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceJSONService.class);

	private InvoiceJSONService() {

	}

	/**
	 * Utilitaire pour lire un fichier JSON et le déserialiser en instance d'Invoice
	 * Le format utilisé est le suivant :
	 * 
	 * <pre>
	 * {
	 *	"customer": {
	 *		"name": "Cornelius"
	 *	},
	 *	"lines": [
	 *		{
	 *			"item": {
	 *				"name": "chevilles pour brique",
	 *				"unitPrice": 0.05
	 *			},
	 *			"quantity": 50
	 *		},
	 *		{
	 *			"item": {
	 *				"name": "vis placo",
	 *				"unitPrice": 0.01
	 *			},
	 *			"quantity": 1000
	 *		}
	 *	]
	 * }
	 * 
	 * </pre>
	 * 
	 * @param file le fichier d'entrée en JSON
	 * @return un objet Invoice
	 */
	public static Invoice readFromFile(File file) {

		ObjectMapper om = new ObjectMapper();
		Invoice facture = new Invoice();
		try {
			facture = om.readValue(file, Invoice.class);
		} catch (IOException e) {
			LOGGER.logError("error while reading invoice", e);
		}
		return facture;

	}

}
