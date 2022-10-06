package business.util;

import java.time.LocalDate;

/**
 * Utilidades para trabajar con fechas.
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class DateUtils {

	/**
	 * Comprueba que una fecha es anterior a otra, pasadas como parametro.
	 * 
	 * @param dateToCheck
	 * @param dateToCompare
	 * @return true si la fecha dateToCheck es anterior a dateToCheck y false en
	 *         caso contrario.
	 */
	public static boolean checkDateIsBefore(final LocalDate dateToCheck, final LocalDate dateToCompare) {
		return dateToCheck.isBefore(dateToCompare);
	}

	/**
	 * Comprueba que una fecha es posterior a otra, pasadas como parametro.
	 * 
	 * @param dateToCheck
	 * @param dateToCompare
	 * @return true si la fecha dateToCheck es posterior a dateToCheck y false en
	 *         caso contrario.
	 */
	public static boolean checkDateIsAfter(final LocalDate dateToCheck, final LocalDate dateToCompare) {
		return dateToCheck.isAfter(dateToCompare);
	}
}
