package business.util;

import java.text.DecimalFormat;
import java.time.LocalDate;

import business.BusinessException;
import business.colegiado.crud.ColegiadoServiceImpl;

public class GeneradorNumeroColegiado {

	private static final String NUMBER_SIZE_DEFAULT = "0000";

	/**
	 * Genera un número de colegiado en formato aaaa_nnnn, siendo aaaa el año actual
	 * y nnnn el número siguiente al último número de colegiado.
	 * 
	 * @return Número de colegiado en formato aaaa_nnnn
	 * @throws BusinessException
	 */
	public static String generateNumber() throws BusinessException {
		StringBuilder sB = new StringBuilder();

		int currentYear = LocalDate.now().getYear();

		sB.append(currentYear);
		sB.append("-");

		int lastColegiadoNumber = new ColegiadoServiceImpl().findAllSolicitudesColegiado().size() + 1;
		DecimalFormat df = new DecimalFormat(NUMBER_SIZE_DEFAULT);
		String lastColegiadoNumberFormatted = df.format(lastColegiadoNumber);

		sB.append(lastColegiadoNumberFormatted);

		return sB.toString();
	}
}