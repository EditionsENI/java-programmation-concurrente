package fr.eni.invoice.services.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.invoice.datamodel.Invoice;
import fr.eni.invoice.datamodel.InvoiceDT;
import fr.eni.invoice.services.logging.Logger;
import fr.eni.invoice.services.logging.LoggerFactory;
import fr.eni.invoice.services.logging.ThreadContext;


/**
 * Service d'accès aux données pour les Factures
 * @author tbrou
 *
 */
public class InvoiceDataService {

	private static InvoiceDataService instance;

	private Object totalSync = new Object();
	private BigDecimal total = BigDecimal.ZERO;

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceDataService.class); 
	
	/**
	 * Enregistrement d'une facture en base, seuls les informations suivantes
	 * @param invoice
	 */
	public void record(Invoice invoice) {
		Double somme = invoice.getLines().stream()
				.mapToDouble(l -> l.getQuantity() * l.getItem().getUnitPrice().doubleValue()).sum();
		BigDecimal totalFacture = BigDecimal.valueOf(somme);
		synchronized (totalSync) {
			total = total.add(totalFacture);
		}
		DBConnectionProvider provider = DBConnectionProvider.getInstance();
		Connection cnx = null;
		try {
			
			cnx = provider.acquire();
		}catch(InterruptedException ie) {
			LOGGER.logError("problème de récupération de la connexion");
			Thread.currentThread().interrupt();
		}
		if (cnx == null) {
			return;
		}
		try {
			PreparedStatement statement = cnx.prepareStatement("INSERT INTO INVOICES (CUSTOMER, AMOUNT, DETAILS) VALUES(?,?,?)");
			statement.setString(1, invoice.getCustomer().getName());
			statement.setBigDecimal(2, totalFacture);
			statement.setString(3, invoice.getLines().size() + " lignes de factures");
			statement.execute();
		} catch ( SQLException ie) {
			LOGGER.logError("problème d'accès en base");
		}finally {
			provider.release(cnx);
		}

	}
	/**
	 * Récupération du résumé des factures
	 * @param invoice
	 */
	public List<InvoiceDT> fetchSummary() {

		List<InvoiceDT> results = new ArrayList<>();
		Connection cnx = null;
		DBConnectionProvider provider = DBConnectionProvider.getInstance();
		try {
			
			cnx = provider.acquire();
		}catch(InterruptedException ie) {
			LOGGER.logError("problème de récupération de la connexion");
			Thread.currentThread().interrupt();
		}
		if (cnx == null) {
			return new ArrayList<>();
		}
		try  {
			PreparedStatement statement = cnx.prepareStatement("SELECT ID, CUSTOMER, AMOUNT, DETAILS FROM INVOICES ORDER BY CUSTOMER");
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Integer id = rs.getInt(1);
				String customer = rs.getString(2);
				BigDecimal amount = rs.getBigDecimal(3);
				String details = rs.getString(4);
				InvoiceDT invoiceDT = new InvoiceDT();
				invoiceDT.setId(id);
				invoiceDT.setAmount(amount);
				invoiceDT.setCustomer(customer);
				invoiceDT.setDetails(details);
				results.add(invoiceDT);
			}
			statement.close();
			rs.close();
		} catch ( SQLException ie) {
			LOGGER.logError("problème d'accès en base");
			
		}finally {
			LOGGER.logDebug("query finished for this context (user machine identifier / usage identifier) " + ThreadContext.getContext("machine-id")+ "/" + ThreadContext.getContext("usage-id"));
			provider.release(cnx);
		}
		return results;
		
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	private InvoiceDataService() {
	
	}

	public static InvoiceDataService getInstance() {
		if (instance == null) {
			instance = new InvoiceDataService();
		}
		return instance;
	}

}
