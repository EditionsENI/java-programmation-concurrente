package fr.eni.invoice.services.logging;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


/**
 * Implémentation du Logger en Non-blocking IO
 * @author tbrou
 *
 */
public class LoggerNIOImpl extends AbstractLogger implements Logger {

	WritableByteChannel channel;


	@Override
	public void logMessage(String level, String message) {
		if (channel == null) {
			try {
				channel = FileChannel.open(Paths.get("logs/applicationNIO.log"), StandardOpenOption.APPEND);
			} catch (IOException e) {
				//en cas d'erreur on se rabat sur la console
				e.printStackTrace();
				channel = Channels.newChannel(System.out);
			}
		}
		try {
			String line = prepareMessage(level, message)+ "\n";
			channel.write(ByteBuffer.wrap(line.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
