package fr.eni.invoice.services.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;


/**
 * Classe générique centralisant les opérations communes pour émettre les traces
 * @author tbrou
 *
 */
public abstract class AbstractLogger implements Logger {
	
	
	
	protected Class<?> loggingClass;
	


	public AbstractLogger(Class<?> loggingClass) {
		this.loggingClass = loggingClass;
	}

	protected String prepareMessage(String level, String message) {
		String name = "default"; 
		if (this.loggingClass != null) {
			name = this.loggingClass.getName();
		}
		String print = new Date() + "-    " + level + " \t- " + name + "  \t- " + message;
		System.out.println(print);
		return message;
	}
	
	protected AbstractLogger() {
		
	}
	
	public void setLoggingClass(Class<?> loggingClass) {
		this.loggingClass = loggingClass;
	}

	@Override
	public void logInfo(String message) {
		logMessage(Logger.INFO, message);
	}

	@Override
	public void logDebug(String message) {
		logMessage(Logger.DEBUG, message);
		
	}

	@Override
	public void logError(String message) {
		logMessage(Logger.ERROR, message);
		
	}
	
	@Override
	public void logWarn(String message) {
		logMessage(Logger.WARN, message);
		
	}

	@Override
	public void logError(String message, Exception e) {
		StringWriter writer = new StringWriter();
		
		e.printStackTrace(new PrintWriter(writer));
	
		logMessage(ERROR, message + "\n stackTrace : \n"  + writer.toString());
		
		
	}

	
	

	

}
