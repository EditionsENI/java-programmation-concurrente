package fr.eni.invoice.services.logging;

import java.io.File;
import java.io.PrintWriter;

/**
 * Implémentation du logger en Blocking IO
 * @author tbrou
 *
 */
public class LoggerBIOImpl extends AbstractLogger implements Logger {

	private static File logFile = new File("logs/applicationBIO.log");
	private static PrintWriter writer;
	private Object writerSync = new Object();

	@Override
	public void logMessage(String level, String message) {

		synchronized (writerSync) {
			if (writer == null) {
				try {
					new File("logs").mkdirs();
					writer = new PrintWriter(logFile);
				} catch (Exception e) {
					// impossible d'initialiser l'accès au fichier, on se rabat sur la console
					writer = new PrintWriter(System.out);
				}
			}
			writer.println(prepareMessage(level, message));
			writer.flush();
		}

	}

}
