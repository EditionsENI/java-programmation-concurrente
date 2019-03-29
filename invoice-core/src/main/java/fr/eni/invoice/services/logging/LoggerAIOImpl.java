package fr.eni.invoice.services.logging;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Implémentation du logger en Asynchronous IO (ou NIO 2)
 * @author tbrou
 *
 */
public class LoggerAIOImpl extends AbstractLogger implements Logger {

	AsynchronousFileChannel channel;

	AtomicLong size = new AtomicLong(0);

	@Override
	public void logMessage(String level, String message) {
		String line = prepareMessage(level, message) + "\n";
		if (channel == null) {
			try {
				channel = AsynchronousFileChannel.open(Paths.get("logs/applicationAIO.log"), StandardOpenOption.WRITE);
			} catch (IOException e) {
				// en cas d'erreur on se rabat sur la console
				e.printStackTrace();
				System.out.println(line);
				return;
			}
		}

		try {
			channel.write(ByteBuffer.wrap(line.getBytes()), channel.size());
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}

	}

}
