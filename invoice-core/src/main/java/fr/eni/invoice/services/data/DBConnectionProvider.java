package fr.eni.invoice.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import fr.eni.invoice.services.logging.Logger;
import fr.eni.invoice.services.logging.LoggerFactory;
import fr.eni.invoice.services.logging.ThreadContext;


/**
 * Utilitaire pour la fourniture de connexions. Cette classe est un singleton
 * @author tbrou
 *
 */
public class DBConnectionProvider {
	
	//TODO rendre cette taille configurable
	private static final int TAILLE_POOL = 3;

	private static DBConnectionProvider provider;
	private Queue<Connection> availableConnections = new LinkedList<>();
	private List<Connection> usedConnections = new LinkedList<>();
	
	private Semaphore connectionSemaphore;
	
	
	
	
	private DBConnectionProvider()  {
		for (int i = 0; i < TAILLE_POOL; i++) {
			try {
				Class.forName("org.h2.Driver");
				//FIXME lire ces informations de la configuration
				availableConnections.add(DriverManager.getConnection("jdbc:h2:tcp://localhost/./invoices", "root", "root"));
			} catch (SQLException | ClassNotFoundException sqlException) {
				sqlException.printStackTrace();
			}
		}
		connectionSemaphore = new Semaphore(availableConnections.size());
		
	}
	
	/**
	 * 
	 * @return l'instance courante de DBConnectionProvider
	 */
	public static DBConnectionProvider getInstance() {
		if (provider == null) {
			provider = new DBConnectionProvider();
		}
		return provider;
	}
	
	
	/**
	 * 
	 * @return une connexion de base de données.
	 * @throws InterruptedException
	 */
	public Connection acquire() throws InterruptedException {
		connectionSemaphore.acquire();
		System.out.println("connexion prise, reste " + connectionSemaphore.availablePermits() + " connexion(s), " + connectionSemaphore.getQueueLength() + " threads bloqué(s)" );
		Connection connection = availableConnections.poll();
		usedConnections.add(connection);
		return connection;
		
	}
	
	/**
	 * 
	 * @param connection : l'instance à relâcher
	 */
	public void release(Connection connection) {
		usedConnections.remove(connection);
		availableConnections.add(connection);
		connectionSemaphore.release();
		System.out.println("connexion relâchée, reste " + connectionSemaphore.availablePermits() + " connexion(s) - thread id = " + ThreadContext.getContext("thread-id"));
		
	}

	
	
}
