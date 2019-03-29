package fr.eni.invoice.services.logging;

/**
 * Représente un objet de service pour émettre des traces dans l'application
 * @author tbrou
 *
 */
public interface Logger {
	
	public static final String INFO ="INFO";
	public static final String DEBUG ="DEBUG";
	public static final String WARN ="WARN";
	public static final String ERROR ="ERROR";
	
	/**
	 * Emet un message en niveau "INFO"
	 * @param message
	 */
	public void logInfo(String message);
	
	/**
	 * Emet un message en niveau "DEBUG"
	 * @param message
	 */
	public void logDebug(String message);
	
	/**
	 * Emet un message en niveau "ERROR"
	 * @param message
	 */

	public void logError(String message);
	/**
	 * Emmet un message en niveau "ERROR"
	 * @param message
	 * @param exception dont il faut afficher la pile de trace (stacktrace)
	 */
	public void logError(String message, Exception e);
	
	/**
	 * Emet un message en niveau "WARNING"
	 * @param message
	 * @param exception
	 */
	public void logWarn(String message);
	 
		
	/**
	 * Emet un message avec un niveau (level) à préciser.
	 * @param level
	 * @param message
	 */
	public void logMessage(String level, String message);
	
	
}
