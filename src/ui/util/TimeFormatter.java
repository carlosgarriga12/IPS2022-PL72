package ui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.MaskFormatter;

/**
 * Formateador de fechas para campos JFormattedTextField.
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class TimeFormatter extends MaskFormatter {

	private static final long serialVersionUID = -1192719499930459373L;

	public static final String DEFAULT_DATE_MASK = "##/##/####";

	public TimeFormatter() {
		try {
			setMask(DEFAULT_DATE_MASK);
			setPlaceholderCharacter('0');
			setAllowsInvalid(false);
			setOverwriteMode(true);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object stringToValue(String string) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (string == null) {
			string = "00/00/0000";
		}
		return df.parse(string);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (value == null) {
			value = new Date();
		}
		return df.format((Date) value);
	}
}
