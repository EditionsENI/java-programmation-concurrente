package fr.eni.invoice.tests;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import fr.eni.invoice.services.logging.Logger;
import fr.eni.invoice.services.logging.LoggerAIOImpl;
import fr.eni.invoice.services.logging.LoggerBIOImpl;
import fr.eni.invoice.services.logging.LoggerNIOImpl;

public class TestLogging {

	private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
	@Test
	public void testBIOLog() throws InterruptedException {
		Logger logger = new LoggerBIOImpl();
		executeWithinThreads(logger, "message from thread to bio");
		
	}

	@Test
	public void testNIOLog() throws InterruptedException {
		Logger logger = new LoggerNIOImpl();
		executeWithinThreads(logger, "message from thread to nio");
		
	}
	@Test
	public void testAIOLog() throws InterruptedException {
		Logger logger = new LoggerAIOImpl();
		executeWithinThreads(logger, "message from thread to aio");
	}

	private void executeWithinThreads(Logger logger, final String message) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
		for (int i = 0; i < 100000*AVAILABLE_PROCESSORS ; i++) {
			executor.submit(new Runnable() {
				
				@Override
				public void run() {
					logger.logDebug(Thread.currentThread().getId() +" "+ message);					
				}
			});
		}
		executor.shutdown();//synchronisation
		while (!executor.isTerminated()) {
			TimeUnit.MILLISECONDS.sleep(500);
			
		}
		logger.logDebug("end of executions");
	}
	
}
