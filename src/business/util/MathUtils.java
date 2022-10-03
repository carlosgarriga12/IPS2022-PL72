package business.util;

import java.math.MathContext;
import java.math.RoundingMode;

public class MathUtils {

	private static final int DEFAULT_BIGDECIMAL_PRECISION = 32;
	private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;
	public static final MathContext MATH_CONTEXT = new MathContext(DEFAULT_BIGDECIMAL_PRECISION, DEFAULT_ROUNDING_MODE);
}
