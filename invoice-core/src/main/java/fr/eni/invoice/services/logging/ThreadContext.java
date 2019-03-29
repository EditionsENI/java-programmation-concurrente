package fr.eni.invoice.services.logging;

import java.util.LinkedHashMap;
import java.util.Map;

public class ThreadContext {
	
	private ThreadContext() {
		
	}
	
	private static final ThreadLocal<Map<String,String>> context = new ThreadLocal<>();
	
	/**
	 * Stocke un couple cl� valeur dans le contexte de thread courant
	 * @param key
	 * @param value
	 */
	public static void setContextInformation(String key, String value) {
		
		
		Map<String, String> map = context.get();
		if (map == null) {
			map = new LinkedHashMap<>();
		}
		map.put(key, value);
		context.set(map);
		
	}
	
	/**
	 * r�cup�re une information de contexte par la cl�.
	 * @param key
	 * @return
	 */
	public static String getContext(String key) {
	
		Map<String, String> map = context.get();
		if (map == null) {
			map = new LinkedHashMap<>();
			context.set(map);
			return "";
		}else {
			return map.get(key);
		}
	}

	
	/**
	 * Enl�ve les informations de contexte attach�es au thread
	 */
	public static void clearContextInformation() {
		context.set(null);
		
	}

}
