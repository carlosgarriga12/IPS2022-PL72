package business.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

	/**
	 * Convierte una fecha en formato cadena a LocalDate.
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate convertStringIntoLocalDate(final String date) {
		String day = date.split("/")[0];
		String month = date.split("/")[1];
		String year = date.split("/")[2];

		String parsedDate = year.concat("-").concat(month).concat("-").concat(day);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(parsedDate, formatter);
	}
}
