package business.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathUtils {

	public static final int DEFAULT_PRECISION = 2;
	public static final String DEFAULT_DECIMALS_PATTERN = "#.##";
	public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

	/**
	 * Redondeo de un número a {@link DEFAULT_DECIMALS_PATTERN}
	 * 
	 * @param numberToRound Número flotante a redondear.
	 * @return
	 * 
	 * @throws NumberFormatException Si el número no es válido.
	 */
	public static double roundNumberToNDecimals(double numberToRound) throws NumberFormatException {
		DecimalFormat df = new DecimalFormat(DEFAULT_DECIMALS_PATTERN);
		df.setRoundingMode(DEFAULT_ROUNDING_MODE);

		return Double.parseDouble(df.format(numberToRound));
	}
}
