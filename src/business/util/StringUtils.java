package business.util;

import java.text.Normalizer;

/**
 * Utilidades para manejo de cadenas de texto.
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class StringUtils {

	/**
	 * Normaliza la cadena de texto indicada.
	 * 
	 * Elimina los acentos y diacríticos de la cadena.
	 * 
	 * @param cadenaOriginal Cadena sin normalizar.
	 * @return Cadena normalizada sin tildes, acentos o diacríticos.
	 */
	public static String normalizarCadenaTexto(String cadenaOriginal) {
		return Normalizer.normalize(cadenaOriginal, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase()
				.trim();
	}

	/**
	 * Capitaliza una cadena pasada como paremetro. Por ejemplo, dada la cadena JOHN
	 * --> John Esta función únicamente capitaliza una palabra.
	 * 
	 * @param text Cadena a capitalizar.
	 * @return Cadena capitalizada.
	 */
	public static String capitalize(String text) {
		return text.length() < 2 ? text : text.substring(0, 1).toUpperCase().concat(text.substring(1).toLowerCase());
	}
}
