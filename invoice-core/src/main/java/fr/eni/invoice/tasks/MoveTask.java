package fr.eni.invoice.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Classe qui permet de déplacer un fichier d'un répertoire à l'autre
 * @author tbrou
 *
 */
public class MoveTask implements Runnable {

	private File source;
	private File targetFolder;
	
	@Override
	public void run() {
		try {
			System.out.println("thread : "+Thread.currentThread().getId() + ", déplace le fichier :" + source.getName());
			File targetFile = new File(targetFolder.getAbsolutePath() + File.separator + source.getName());
			Files.move(source.toPath(),targetFile.toPath(), StandardCopyOption.ATOMIC_MOVE);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	
	public MoveTask(File source, File targetFolder) {
		super();
		this.source = source;
		this.targetFolder = targetFolder;
	}
	
	

}
