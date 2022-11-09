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
}
