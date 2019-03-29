package fr.eni.invoice.launcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import fr.eni.invoice.datamodel.Invoice;
import fr.eni.invoice.services.data.InvoiceDataService;
import fr.eni.invoice.tasks.MoveTask;
import fr.eni.invoice.tasks.ReadAndSaveTask;

public class Launcher {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		String scanFolderPath = "work/scanDir";
		String workFolderPath = "work/workDir";
		String computedFolderPath = "work/computedDir";

		LocalDateTime start = LocalDateTime.now();
		
		//Test de l'existence des répertoires
		File workDirFile = new File(workFolderPath);
		File scanDirFile = new File(scanFolderPath);
		File computedDirFile = new File(computedFolderPath);
		if (!workDirFile.exists()) {
			 workDirFile.mkdirs();
		}
		if (!scanDirFile.exists()) {
			scanDirFile.mkdirs();
		}
		if (!computedDirFile.exists()) {
			computedDirFile.mkdirs();
		}
		
		
		//Création d'un executor pour gérer l'exécution des threads
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		final ExecutorService scanToWorkMoveExecutor = Executors.newFixedThreadPool(availableProcessors);
		Files.list(scanDirFile.toPath()).forEach(p -> 
			scanToWorkMoveExecutor.submit(new MoveTask(p.toFile(), workDirFile))
		);
		
		
		
		
		
		scanToWorkMoveExecutor.shutdown();
		scanToWorkMoveExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
		
		final ExecutorService readExecutor = Executors.newFixedThreadPool(availableProcessors);
		List<Future<Invoice>> futureResults = new ArrayList<>();
		Files.list(workDirFile.toPath()).forEach(p -> 
			futureResults.add(readExecutor.submit(new ReadAndSaveTask(p.toFile())))
			
		);
		while(!futureResults.isEmpty()) {
			int i = 0;
			while (i < futureResults.size()) {
				Future<Invoice> f = futureResults.get(i);
				if (f.isDone()) {
					Invoice facture = f.get();
					System.out.println("facture pour le client " + facture.getCustomer().getName() + "  :traitée");
					futureResults.remove(i);
				}
				i++;
			}
		}
		
		final ExecutorService workToComputedMoveExecutor = Executors.newFixedThreadPool(availableProcessors);
		Files.list(workDirFile.toPath()).forEach(p -> 
			workToComputedMoveExecutor.submit(new MoveTask(p.toFile(), computedDirFile))
		);
		workToComputedMoveExecutor.shutdown();
		while (!workToComputedMoveExecutor.isTerminated()) {
			TimeUnit.MILLISECONDS.sleep(500);
		}
		
		
		
		System.out.println("total facturé : " + InvoiceDataService.getInstance().getTotal().doubleValue());
		LocalDateTime end = LocalDateTime.now();
		System.out.println("temps passé : " + ChronoUnit.MILLIS.between(start, end) + "ms");
		
	}

}
