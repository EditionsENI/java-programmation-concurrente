package fr.eni.invoice.services.logging;

public class LoggerFactory {

	
	private LoggerFactory() {
		
	}
	
	public static Logger getLogger(Class<?> loggingClass) {
		LoggerBIOImpl loggerBIOImpl = new LoggerBIOImpl();
		loggerBIOImpl.setLoggingClass(loggingClass);
		return loggerBIOImpl;
	}
}
