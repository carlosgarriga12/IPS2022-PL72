package ui.components.placeholder;

import java.awt.Font;

import javax.swing.JTextField;

/**
 * Placeholder para los componentes JTextField de la aplicacion.
 * 
 * @author Francisco Coya
 * @version v1.0.0
 *
 */
public class TextPlaceHolderCustom {

	public static void setPlaceholder(final String placeholderText, final JTextField textField) {
		TextPrompt placeholder = new TextPrompt(placeholderText, textField);
		placeholder.changeAlpha(0.75f);
		placeholder.changeStyle(Font.ITALIC);
	}

}
