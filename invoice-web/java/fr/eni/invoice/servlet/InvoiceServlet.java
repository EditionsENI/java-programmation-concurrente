package fr.eni.invoice.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.eni.invoice.datamodel.InvoiceDT;
import fr.eni.invoice.services.InvoiceDataService;
import fr.eni.invoice.services.ThreadContext;

@WebServlet(urlPatterns="/invoices")
public class InvoiceServlet extends HttpServlet{

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String remoteAddr = req.getRemoteAddr();
		ThreadContext.setContextInformation("remote-user", remoteAddr);
		
		List<InvoiceDT> listInvoices = InvoiceDataService.getInstance().fetchAll();
		ObjectMapper objectMapper = new ObjectMapper();
		
		String listAsJson = objectMapper.writer().writeValueAsString(listInvoices);
		resp.getWriter().println(listAsJson);
		
		ThreadContext.clearContextInformation();
		
	}
	
	
	
	
	
	

}
